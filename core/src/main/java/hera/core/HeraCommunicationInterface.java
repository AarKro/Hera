package hera.core;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.GuildChannel;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Scanner;

public class HeraCommunicationInterface {
	private static final Logger LOG = LoggerFactory.getLogger(HeraCommunicationInterface.class);

	private GatewayDiscordClient client;

	private Guild activeGuild;

	private GuildChannel activeChannel;

	private boolean isEstablished;

	public HeraCommunicationInterface(GatewayDiscordClient client) {
		this.client = client;
	}

	public void startupHCI() {
		LOG.info("Starting Hera Communication Interface (HCI)");

		Thread thread = new Thread(() -> {
			LOG.info("Subscribe HCI to message updates");
			client.getEventDispatcher().on(MessageCreateEvent.class)
					.filter(event -> activeChannel != null)
					.flatMap(event -> event.getGuild()
							.flatMap(guild -> event.getMessage().getChannel()
									.filter(channel -> channel.getId().asLong() == activeChannel.getId().asLong())
									.flatMap(channel -> Mono.justOrEmpty(event.getMessage().getContent())
											.flatMap(content -> Mono.justOrEmpty(event.getMember())
													.doOnNext(member -> updateMessageDisplay(member, content, false))
											)
									)
							)
					).subscribe();

			LOG.info("Subscribe HCI to console input");
			LOG.info("HCI is now ready to be used");
			Flux.create(sink -> {
				Scanner scanner = new Scanner(System.in);
				while (scanner.hasNext()) {
					sink.next(scanner.nextLine());
				}
			})
			.subscribe(obj -> {
				String input = (String) obj;
				String command = "";
				String body = "";
				if (input.startsWith(">")) {
					String[] parts = input.split(" ");
					if (parts.length > 1) {
						command = parts[1];
						for (int i = 2; i < parts.length; i++) {
							body += parts[i] + " ";
						}
						body = body.trim();
					}

					switch (command) {
						case "u":
						case "use":
							selectGuild(body);
							break;
						case "ct":
						case "connectTo":
							if (activeGuild != null) selectChannel(body);
							break;
						case "sg":
						case "showGuilds":
							showGuilds();
							break;
						case "sc":
						case "showChannels":
							if (activeGuild != null) showChannels();
							break;
						case "dc":
						case "disconnect":
							System.out.println("< disconnect from active guild and channel");
							activeGuild = null;
							activeChannel = null;
							break;
					}
				} else if (activeChannel != null && !input.equals("")) {
					((GuildMessageChannel) activeChannel).createMessage(input).subscribe();
				}
			});
		});

		thread.start();
	}

	private void updateMessageDisplay(Member member, String message, boolean includeSelf) {
		Mono.justOrEmpty(client.getSelfId())
				.filter(id -> includeSelf || id.asLong() != member.getId().asLong())
				.doOnNext(id -> System.out.println(member.getDisplayName() + " < " + message))
				.subscribe();
	}

	private void selectGuild(String input) {
		getGuild(input).doOnNext(guild -> {
			System.out.println("< selecting guild " + guild.getName());
			setActiveGuild(guild);
		}).subscribe();
	}

	private void selectChannel(String input) {
		getChannel(input)
				.filter((guildChannel -> guildChannel.getType() == Channel.Type.GUILD_TEXT))
				.doOnNext(guildChannel -> {
					System.out.println("< selecting channel " + guildChannel.getName());
					setActiveChannel(guildChannel);
				}).subscribe();
	}

	private void showGuilds() {
		client.getGuilds().doOnNext(guild -> System.out.println("< " + guild.getName())).subscribe();
	}

	private void showChannels() {
		activeGuild.getChannels()
				.filter(channel -> channel.getType() == Channel.Type.GUILD_TEXT)
				.doOnNext(channel -> System.out.println("< " + channel.getName()))
				.subscribe();
	}

	private Flux<Guild> getGuild(String input) {
		return client.getGuilds().filter(guild -> guild.getName().toUpperCase().equals(input.toUpperCase()));
	}

	private Flux<GuildChannel> getChannel(String input) {
		return activeGuild.getChannels().filter(guildChannel -> guildChannel.getName().toUpperCase().equals(input.toUpperCase()));
	}

	public GatewayDiscordClient getClient() {
		return client;
	}

	public void setClient(GatewayDiscordClient client) {
		this.client = client;
	}

	public Guild getActiveGuild() {
		return activeGuild;
	}

	public void setActiveGuild(Guild activeGuild) {
		this.activeGuild = activeGuild;
	}

	public GuildChannel getActiveChannel() {
		return activeChannel;
	}

	public void setActiveChannel(GuildChannel activeChannel) {
		this.activeChannel = activeChannel;
	}

	public boolean isEstablished() {
		return isEstablished;
	}

	public void setEstablished(boolean established) {
		isEstablished = established;
	}
}
