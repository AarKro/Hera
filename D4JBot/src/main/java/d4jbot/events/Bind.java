package d4jbot.events;

import d4jbot.misc.ChannelBinder;
import d4jbot.misc.MessageSender;
import d4jbots.enums.BotPrefix;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Bind {

	// default constructor
	public Bind() { }
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "bind")) {
			
			if(e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				ChannelBinder.bindChannel(e.getChannel());
			} else {
				MessageSender.sendMessage(e.getChannel(), true, "You need to be an Administrator of this server to use this command.");
			}
			
		}
	}
}
