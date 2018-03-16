package hera.events;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;

public class AutoAssignRole {

	// default constructor
	public AutoAssignRole() { }
	
	@EventSubscriber
	public void onUserJoin(UserJoinEvent e) {
		try {
			e.getUser().addRole(e.getGuild().getRolesByName("Casual").get(0));
		} catch(Exception error) {
			// nothing
		}
	}
}
