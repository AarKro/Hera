package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BotSettings;
import hera.enums.BoundChannel;
import hera.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Volume implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Volume.class);
	
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
		LOG.debug("Start of: Volume.execute");
		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
			String[] args = e.getMessage().getContent().split(" ");

			if (args.length == 2) {
				LOG.debug("Enough parameters to interpret command: " + args.length);
				try {
					int volume = Integer.parseInt(args[1]);
					if (volume >= 0 && volume <= 150) {
						gapm.getGuildAudioPlayer(e.getGuild()).player.setVolume(volume);
						BotSettings.BOT_VOLUME.setPropertyValue(Integer.toString(volume));
						LOG.info(e.getAuthor() + " set volume to " + volume);
					} else {
						ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Volume level must be between 0 and 150");
						LOG.debug(e.getAuthor() + " provided input that is not within the defined bounds of 0 and 150. Input: " + volume);
					}
				} catch (NumberFormatException e2) {
					ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Volume level must be a number");
					LOG.error(e.getAuthor() + " provided input that is not a number");
					LOG.error(e2.getMessage() + " : " + e2.getCause());
				}
			} else {
				ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "", "Invalid usage!\nSyntax: $volume <0 - 150>");
				LOG.debug(e.getAuthor() + " used command volume wrong");
			}
		} else {
			ms.sendMessage(e.getChannel(), "", "You need to be an Administrator of this server to use this command.");
			LOG.debug(e.getAuthor() + " used command volume but is not an admin on this server");
		}
		LOG.debug("End of: Volume.execute");
	}
}
