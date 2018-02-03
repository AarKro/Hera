package d4jbot.events;

import d4jbot.enums.BotSettings;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Join implements Command {
	
	private static Join instance;
	
	public static Join getInstance() {
		if (instance == null) instance = new Join();
		return instance;
	}

	// default constructor
	private Join() { }
	
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "join")) {
			e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel().join();
		}
	}
}
