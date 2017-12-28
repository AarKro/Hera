package d4jbot.events;

import java.util.Random;

import d4jbot.misc.MessageSender;
import d4jbots.enums.BotPrefix;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
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

				if(args.length > 1) {
					
					//				StringBuilder sb = new StringBuilder(args[1]);
					//				sb.insert(2, "!");
					
					Random rnd = new Random();
					for(IUser user : e.getGuild().getUsers()) {
						
						if(args[1].equals(user.getName()) || args[1].equals(user.getNicknameForGuild(e.getGuild()))/* || sb.equals(user.mention())*/ ) {
							boolean success = false;
							while(!success) {
								try {
									IVoiceChannel moveTo = e.getGuild().getVoiceChannels().get(rnd.nextInt(e.getGuild().getVoiceChannels().size()));
									user.moveToVoiceChannel(moveTo);
									success = true;
									ms.sendMessage(e.getChannel(), true, user.mention() + " moved to " + moveTo.mention());
									break;
								} catch(MissingPermissionsException error) { }
							}
							
						}
					}
					
				} else ms.sendMessage(e.getChannel(), true, "Invalid usage of $begone!\nSyntax: $begone <name/nickname>"); 
			} else ms.sendMessage(e.getChannel(), true, "You need to be an Administrator of this server to use this command.");
		}
	}
}
