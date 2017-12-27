package d4jbot.events;

import java.util.Random;

import d4jbot.misc.MessageSender;
import d4jbots.enums.BotPrefix;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MissingPermissionsException;

public class Begone {

	private MessageSender ms;
	
	// default constructor
	public Begone() { }
	
	// constructor
	public Begone(MessageSender ms) {
		this.ms = ms;
	}
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "begone")) {
			if(e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				String[] args = e.getMessage().getContent().split(" ");
				Random rnd = new Random();
				
				for(IUser user : e.getGuild().getUsers()) {
					
					if(args[1].equals(user.getName()) || args[1].equals(user.getNicknameForGuild(e.getGuild()))) {
						boolean success = false;
						while(!success) {
							try {
								user.moveToVoiceChannel(e.getGuild().getVoiceChannels().get(rnd.nextInt(e.getGuild().getVoiceChannels().size())));
								success = true;
								break;
							} catch(MissingPermissionsException error) {
								success = false;
							}
						}
						
					}
				}
					
			} else ms.sendMessage(e.getChannel(), true, "You need to be an Administrator of this server to use this command.");
		}
	}
}
