package hera.events.commands;

import hera.events.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.events.eventSupplements.MessageSender;
import hera.events.eventSupplements.VoteManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class End extends Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(End.class);
	
	private static End instance;

	public static End getInstance() {
		if (instance == null)
			instance = new End();
		return instance;
	}

	private MessageSender ms;
	private VoteManager vm;

	private End() {
		super(null, 0, false);
		this.ms = MessageSender.getInstance();
		this.vm = VoteManager.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		LOG.debug("Start of: End.execute");
		if (vm.isVoteActive()) {
			LOG.debug("There is an active vote");
			// Separate user permissions check necessary since he does not necessarily need admin rights, but can also just be the initiator of the vote
			if (e.getAuthor() == vm.getVoteOrganiser() || e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				LOG.debug(e.getAuthor() + " is an admin or has started the vote, thus can end it");
				ms.sendMessage(e.getChannel(),
						"Vote has ended!", "Topic: " + vm.getVoteTopic() + "\nYes: " + vm.getCountYes() + "\nNo: "
								+ vm.getCountNo() + "\n\nVote was started by " + vm.getVoteOrganiser().mention()
								+ "\nVote was ended by " + e.getAuthor().mention());
				LOG.info(e.getAuthor() + " has ended the active vote");
				vm.resetVote();
				LOG.info("Vote parameters have been reseted. A new vote can be started");
			} else {
				ms.sendMessage(e.getChannel(), "", "You must have started the vote or be an Administrator to end the vote.");
				LOG.debug(e.getAuthor() + " is not an admin or has not started the vote, thus can't end it");
			}
		} else {
			ms.sendMessage(e.getChannel(), "There is no active vote to end", "Type $vote <topic> to start a vote.");
			LOG.debug(e.getAuthor() + " tried to end a non existing vote");
		}
		LOG.debug("End of: End.execute");
	}
}
