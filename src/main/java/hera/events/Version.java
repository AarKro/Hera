package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BotSettings;
import hera.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Version implements Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(Version.class);
	
	private static Version instance;
	
	public static Version getInstance() {
		if (instance == null) {
			instance = new Version();
		}
		return instance;
	}

	private MessageSender ms;
	
	private Version() {
		this.ms = MessageSender.getInstance();
	}
	
	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Version.execute");
		ms.sendMessage(e.getChannel(), BotSettings.BOT_VERSION.getPropertyValue());
		LOG.info(e.getAuthor() + " requested the current version of Hera, which is " + BotSettings.BOT_VERSION.getPropertyValue());
		LOG.debug("End of: Version.execute");
	}
}
