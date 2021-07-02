package hera.core;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.VoiceStateUpdateEvent;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.guild.MemberJoinEvent;
import discord4j.core.event.domain.guild.MemberLeaveEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import hera.core.api.handlers.YouTubeApiHandler;
import hera.core.commands.Commands;
import hera.core.commands.Queue;
import hera.core.commands.Vote;
import hera.core.music.HeraAudioManager;
import hera.database.entities.*;
import hera.database.types.GuildSettingKey;
import hera.database.types.TokenKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static hera.metrics.MetricsLogger.STATS;
import static hera.store.DataStore.STORE;

public class Core {
	private static final Logger LOG = LoggerFactory.getLogger(Core.class);

	public static void main(String[] args) {
		LOG.info("Starting Hera...");

		STORE.initialise();

		LOG.info("Get discord login token from store");
		List<Token> loginTokens = STORE.tokens().forKey(TokenKey.DISCORD_LOGIN);
		if (loginTokens.isEmpty()) {
			LOG.error("No discord login token found in store");
			LOG.error("Terminating startup procedure");
			return;
		}

		LOG.info("Initialising command mappings");
		Commands.initialise();

		LOG.info("Initialising Hera Audio Player");
		HeraAudioManager.initialise();

		LOG.info("Initialising YouTube API Handler");
		YouTubeApiHandler.initialise();

		//final DiscordClient client = new DiscordClientBuilder(loginTokens.get(0).getToken()).build();
		final DiscordClient client = DiscordClient.create(loginTokens.get(0).getToken());
		final GatewayDiscordClient gateway = client.login().block();
		HeraUtil.setClient(gateway);

		HeraCommunicationInterface hci = new HeraCommunicationInterface(gateway);
		hci.startupHCI();

		// on guild join -> Add guild to store if we don't have it already
		gateway.on(GuildCreateEvent.class).subscribe(event-> {
			STORE.guilds().upsert(new Guild(event.getGuild().getId().asLong()));

			// activate some config flags by default
			List<ConfigFlagType> types = STORE.configFlagTypes().getAll();
			List<ConfigFlag> guildFlags = STORE.configFlags().forGuild(event.getGuild().getId().asLong());
			types.forEach(type -> {
				List<ConfigFlag> flags = guildFlags.stream().filter(flag -> flag.getConfigFlagType().getName() == type.getName()).collect(Collectors.toList());
				if (flags.isEmpty()) {
					// flag has not been set yet for that guild, so we turn it on by default
					STORE.configFlags().add(new ConfigFlag(event.getGuild().getId().asLong(), type, type.isDefault()));
				}
			});

			if (gateway.getSelfId() != null) {
				STATS.logHeraGuildJoin(gateway.getSelfId().asLong(), event.getGuild().getId().asLong());
			}

		});


		// on guild join -> Add members of guild to store if we don't have them already

		gateway.on(GuildCreateEvent.class)
				.subscribe(event -> event.getGuild().getMembers()
						.flatMap(member -> {
							STORE.users().upsert(new User(member.getId().asLong()));
							return Mono.empty();
						})
						.next()
				);

		// on user joins guild  -> Add new member to store if we don't have him already
		gateway.on(MemberJoinEvent.class)
				.subscribe(event -> {
					STORE.users().upsert(new User(event.getMember().getId().asLong()));
					STATS.logUserGuildJoin(event.getMember().getId().asLong(), event.getGuildId().asLong());

				});

		// on user joins guild ->
		gateway.on(MemberJoinEvent.class)
				.subscribe(event -> event.getGuild()
						.flatMap(g -> Flux.fromIterable(STORE.guildSettings().forGuildAndKey(g.getId().asLong(), GuildSettingKey.ON_JOIN_ROLE))
								.flatMap(gs -> g.getRoleById(Snowflake.of(gs.getValue())).
										flatMap(r -> event.getMember().addRole(r.getId()))
								)
								.next()
						)
				);

		// log when a user leaves a guild
		gateway.on(MemberLeaveEvent.class)
				.doOnNext(event -> STATS.logUserGuildLeave(event.getUser().getId().asLong(), event.getGuildId().asLong()))
				.subscribe();

		// log when someone joins or leaves a voice channel
		gateway.on(VoiceStateUpdateEvent.class)
				.subscribe(event -> {
					if (event.getCurrent().getChannelId().isPresent()) {
						STATS.logVcJoin(event.getCurrent().getUserId().asLong(), event.getCurrent().getGuildId().asLong());
					} else {
						STATS.logVcLeave(event.getCurrent().getUserId().asLong(), event.getCurrent().getGuildId().asLong());
					}
				});

		// log when message is written
		gateway.on(MessageCreateEvent.class)
				.flatMap(event -> event.getGuild()
						.flatMap(guild -> Mono.justOrEmpty(event.getMember())
								.flatMap(member -> Mono.justOrEmpty(gateway.getSelfId())
										.filter(selfId -> selfId.asLong() != member.getId().asLong())
										.flatMap(selfId -> {
											STATS.logMessage(member.getId().asLong(), guild.getId().asLong());
											return Mono.empty();
										})
								)
						)
				)
				.subscribe();

		// Reaction emoji event stream
		gateway.on(ReactionAddEvent.class)
				.flatMap(event -> Mono.justOrEmpty(gateway.getSelfId())
						.filter(selfId -> event.getUserId().asLong() != selfId.asLong())
						.flatMap(selfId -> event.getGuild()
								.filter(guild -> event.getMessageId().asLong() == HeraAudioManager.getScheduler(guild).getCurrentQueueMessageId())
								.flatMap(guild -> event.getMessage()
										.flatMap(message -> Mono.justOrEmpty(message.getEmbeds().get(0).getFooter())
												.flatMap(footer -> Mono.justOrEmpty(event.getEmoji().asUnicodeEmoji())
														.flatMap(unicode -> Queue.executeFromReaction(event, message, footer.getText(), unicode.getRaw(), guild))
														.then()
												)
										)
								)
								.switchIfEmpty(Mono.just(Vote.ACTIVE_VOTE_MESSAGE_IDS)
										.filter(activeVotes -> activeVotes.containsKey(event.getMessageId().asLong()))
										.flatMap(activeVotes -> event.getGuild()
												.flatMap(guild -> event.getChannel()
														.flatMap(channel -> event.getUser()
																.flatMap(user -> user.asMember(guild.getId())
																		.flatMap(member -> event.getMessage()
																				.flatMap(message -> Mono.justOrEmpty(message.getEmbeds().get(0).getDescription())
																						.flatMap(description -> Mono.justOrEmpty(event.getEmoji().asUnicodeEmoji())
																								.flatMap(unicode -> Vote.executeFromReaction(event, channel, message, description, unicode.getRaw(), member, guild))
																								.then()
																						)
																				)
																		)
																)
														)
												)
										)
								)
						)
				)
				.subscribe();

		// Main event stream. Commands are triggered here
		gateway.on(MessageCreateEvent.class)
				.flatMap(event -> event.getGuild()
						.flatMap(guild -> Flux.fromIterable(STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.COMMAND_PREFIX))
								.flatMap(settings -> Mono.justOrEmpty(settings.getValue()))
								.defaultIfEmpty("$")
								.flatMap(commandPrefix -> Mono.justOrEmpty(event.getMessage().getContent())
										.filter(content -> content.startsWith(commandPrefix))
										.flatMap(content -> Mono.justOrEmpty(HeraUtil.getCommandFromMessage(content, commandPrefix, guild))
												.flatMap(command -> Mono.justOrEmpty(event.getMember())
														.flatMap(member -> event.getMessage().getChannel()
																.filterWhen(channel -> HeraUtil.checkHeraPermissions(command, guild))
																.filterWhen(channel -> HeraUtil.checkPermissions(command, member, guild, channel))
																.filterWhen(channel -> HeraUtil.checkParameters(content, command, channel))
																.flatMap(channel -> Mono.just(HeraUtil.extractParameters(content, command))
																		.flatMap(params -> {
																			// log commands call
																			STATS.logCommand(command, member.getId().asLong(), guild.getId().asLong());
																			// execute commands
																			return Commands.COMMANDS.get(command.getName()).execute(event, guild, member, channel, params);
																		})
																)
														)
												)
										)
								).next()
						)
				)
				.subscribe(aVoid -> Mono.empty(), throwable -> {
					LOG.debug("Top level error catch block:");
					LOG.debug("Stacktrace:", throwable);
				});

		LOG.info("...Hera is ready to use as soon as connection to gateway is established");

		// login
		client.login().block();
	}
}
