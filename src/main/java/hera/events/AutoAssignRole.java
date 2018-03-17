package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BoundChannel;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;

public class AutoAssignRole {

	private static final Logger LOG = LoggerFactory.getLogger(BoundChannel.class);
	
	// default constructor
	public AutoAssignRole() { }
	
	@EventSubscriber
	public void onUserJoin(UserJoinEvent e) {
		LOG.debug("Start of: AutoAssignRole.onUserJoin");
		try {
			LOG.info("Assigning role Casual to newly joined user " + e.getUser().getName());
			e.getUser().addRole(e.getGuild().getRolesByName("Casual").get(0));
		} catch(Exception error) {
			LOG.error("Assigning role for user " + e.getUser().getName() + " failed");
			LOG.error(error.getMessage() + " : " + error.getCause());
		} finally {
			LOG.debug("End of: AutoAssignRole.onUserJoin");
		}
	}
}
