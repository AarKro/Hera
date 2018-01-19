package d4jbot.events;

import d4jbot.enums.BotSettings;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Leave {

	// default constructor
	public Leave() { }
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "leave")) {
			e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel().leave();
		}
	}
}