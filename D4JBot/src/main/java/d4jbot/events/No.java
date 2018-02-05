package d4jbot.events;

import d4jbot.misc.MessageSender;
import d4jbot.misc.VoteManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class No implements Command {

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
		if (vm.isVoteActive()) {
			if (!vm.hasAlreadyVoted(e.getAuthor())) {
				vm.addUserToAlreadyVoted(e.getAuthor());
				vm.setCountNo(vm.getCountNo() + 1);
			} else {
				ms.sendMessage(e.getChannel(), "You have already voted!");
			}
		} else {
			ms.sendMessage(e.getChannel(), "There is no active vote to vote on.\nType $vote <topic> to start a vote.");
		}
	}
}
