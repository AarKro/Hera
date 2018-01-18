package d4jbot.events;

import d4jbot.enums.BotPrefix;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Join {

	// default constructor
	public Join() { }
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "join")) {
			e.getAuthor().getVoiceStateForGuild(e.getGuild()).getChannel().join();
		}
	}
}
