package hera.events;

import hera.misc.MessageSender;
import hera.misc.VoteManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Vote implements Command {

	private static Vote instance;

	public static Vote getInstance() {
		if (instance == null) {
			instance = new Vote();
		}
		return instance;
	}

	private MessageSender ms;
	private VoteManager vm;

	// default constructor
	public Vote() {
		this.ms = MessageSender.getInstance();
		this.vm = VoteManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		String[] args = e.getMessage().getContent().split(" ");

		if (!vm.isVoteActive()) {
			if (args.length > 1) {
				String topic = "";
				for (int i = 1; i < args.length; i++) {
					topic += args[i] + " ";
				}

				vm.startVote(topic, e.getAuthor());

				ms.sendMessage(e.getChannel(),
						"Vote started!\n\nTopic: " + topic + "\nYes ($yes) or No ($no).\n\nType $end to end the vote.");
			} else {
				ms.sendMessage(e.getChannel(), "Give a topic to vote on.\n$vote <topic>");
			}
		} else {
			ms.sendMessage(e.getChannel(), "There is already a vote going on.\n\nTopic: " + vm.getVoteTopic()
					+ "\n\nType $end to end the vote.");
		}
	}
}
