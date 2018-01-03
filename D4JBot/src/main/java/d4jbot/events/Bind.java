package d4jbot.events;

import d4jbot.enums.BotPrefix;
import d4jbot.misc.ChannelBinder;
import d4jbot.misc.MessageSender;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class Bind {

	private MessageSender ms;
	private ChannelBinder cb;
	
	// default constructor
	public Bind() { }
	
	// constructor
	public Bind(MessageSender ms, ChannelBinder cb) {
		this.ms = ms;
		this.cb = cb;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "bind")) {
			
			if(e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				cb.bindChannel(e.getChannel());
			} else {
				ms.sendMessage(e.getChannel(), true, "You need to be an Administrator of this server to use this command.");
			}
			
		}
	}
}
