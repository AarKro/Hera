package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.eventSupplements.MessageSender;
import hera.eventSupplements.VoteManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class No implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(No.class);
	
	private static No instance;

	public static No getInstance() {
		if (instance == null)
			instance = new No();
		return instance;
	}

	private MessageSender ms;
	private VoteManager vm;

	// default constructor
	private No() {
		this.ms = MessageSender.getInstance();
		this.vm = VoteManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: No.execute");
		if (vm.isVoteActive()) {
			if (!vm.hasAlreadyVoted(e.getAuthor())) {
				vm.addUserToAlreadyVoted(e.getAuthor());
				vm.setCountNo(vm.getCountNo() + 1);
				LOG.info(e.getAuthor() + " voted no on the currently active vote");
			} else {
				ms.sendMessage(e.getChannel(), "", "You have already voted!");
				LOG.debug(e.getAuthor() + " tried to vote no on a vote that was already voted on");
			}
		} else {
			ms.sendMessage(e.getChannel(), "There is no active vote to vote on", "Type $vote <topic> to start a vote.");
			LOG.debug(e.getAuthor() + " tried to vote no on a non existing vote");
		}
		LOG.debug("End of: No.execute");
	}
}
