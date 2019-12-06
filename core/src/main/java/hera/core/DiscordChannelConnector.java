package hera.core;

import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Scanner;

public class DiscordChannelConnector {
	private DiscordClient client;

	private Guild activeGuild;

	private GuildChannel activeChannel;

	private boolean isEstablished;

	public DiscordChannelConnector(DiscordClient client) {
		this.client = client;
	}

	public void startupConnector() {
		Thread thread = new Thread(() -> {
			Flux.create(sink -> {
				Scanner scanner = new Scanner(System.in);
				while (scanner.hasNext()) {
					sink.next(scanner.nextLine());
				}
			})
			.subscribe(obj -> {
				String input = (String) obj;
				int index = input.indexOf(" ");
				if (index == -1) index = 0;
				String command = input.substring(0, index).trim();
				String body = input.substring(index).trim();

				switch (command) {
					case "u":
					case "use":
						selectGuild(body);
						break;
					case "ct":
					case "connectTo":
						if (activeGuild != null) selectChannel(body);
						break;
					default:
						if (activeChannel != null) ((MessageChannel) activeChannel).createMessage(input).block();
				}
			});
		});

		thread.start();
	}

	public void updateMessageDisplay(Member member, String message) {
		Mono.justOrEmpty(client.getSelfId())
				.filter(id -> id.asLong() != member.getId().asLong())
				.subscribe(id -> System.out.println(member.getDisplayName() + " <<< " + message));
	}

	private void selectGuild(String input) {
		getGuild(input).doOnNext(guild -> {
			System.out.println("selecting guild " + guild.getName());
			setActiveGuild(guild);
		}).blockFirst();
	}

	private void selectChannel(String input) {
		getChannel(input).doOnNext(guildChannel -> {
			System.out.println("selecting channel " + guildChannel.getName());
			setActiveChannel(guildChannel);
		}).blockFirst();
	}

	private Flux<Guild> getGuild(String input) {
		return client.getGuilds().filter(guild -> guild.getName().toUpperCase().equals(input.toUpperCase()));
	}

	private Flux<GuildChannel> getChannel(String input) {
		return activeGuild.getChannels().filter(guildChannel -> guildChannel.getName().toUpperCase().equals(input.toUpperCase()));
	}

	public DiscordClient getClient() {
		return client;
	}

	public void setClient(DiscordClient client) {
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
