package d4jbot.events;

import d4jbot.enums.BotSettings;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Leave implements Command {

	private static Leave instance;
	
	public static Leave getInstance() {
		if (instance == null) instance = new Leave();
		return instance;
	}
	
	// default constructor
	private Leave() { }
	
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "leave")) {
			e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel().leave();
		}
	}
}