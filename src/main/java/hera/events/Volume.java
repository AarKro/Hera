package hera.events;

import hera.enums.BotSettings;
import hera.enums.BoundChannel;
import hera.misc.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Volume implements Command {

	private static Volume instance;

	public static Volume getInstance() {
		if (instance == null) {
			instance = new Volume();
		}
		return instance;
	}

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;

	// constructor
	public Volume() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			String[] args = e.getMessage().getContent().split(" ");

			if (args.length == 2) {
				try {
					int volume = Integer.parseInt(args[1]);
					if (volume >= 0 && volume <= 150) {
						gapm.getGuildAudioPlayer(e.getGuild()).player.setVolume(volume);
						BotSettings.BOT_VOLUME.setPropertyValue(Integer.toString(volume));
					} else
						ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Volume level must be between 0 and 150");
				} catch (NumberFormatException e2) {
					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Volume level must be a number");
				}
			} else
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Invalid usage!\nSyntax: $volume <0 - 150>");
		} else
			ms.sendMessage(e.getChannel(), "You need to be an Administrator of this server to use this command.");
	}
}
