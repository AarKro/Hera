package d4jbot.events;

import java.util.Random;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;

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
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "begone")) {
			if(e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR) || e.getAuthor().getRolesForGuild(e.getGuild()).contains(e.getGuild().getRolesByName("BeGone").get(0))) {
				String[] args = e.getMessage().getContent().split(" ");

				if(args.length > 1) {
				
					String username = "";
					for(int i = 1; i < args.length; i++) {
						username += args[i] + " ";
					}
					username = username.trim();
					
//					StringBuilder sb = new StringBuilder(username);
//					sb.insert(2, "!");
//					Does not work :/
																		
					Random rnd = new Random();
					for(IUser user : e.getGuild().getUsers()) {
						
						if(username.equals(user.getName()) || username.equals(user.getNicknameForGuild(e.getGuild()))/* || sb.equals(user.mention())*/ ) {
						
							boolean success = false;
							while(!success) {
							
								IVoiceChannel moveTo = e.getGuild().getVoiceChannels().get(rnd.nextInt(e.getGuild().getVoiceChannels().size()));
								if(moveTo.getModifiedPermissions(user).contains(Permissions.VOICE_CONNECT)) {
									user.moveToVoiceChannel(moveTo);
									success = true;
									ms.sendMessage(e.getChannel(), true, user.mention() + " moved to " + moveTo.getName());
								}															
						
							}
						
						}
												
					}
			
				} else ms.sendMessage(e.getChannel(), true, "Invalid usage of $begone.\nSyntax: $begone <name/nickname>"); 
			} else ms.sendMessage(e.getChannel(), true, "You need to be an Administrator of this server or possess the BeGone role to use this command.");
		}
	}
}
