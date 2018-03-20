package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.misc.MessageSender;
import hera.misc.VoteManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Vote implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Vote.class);
	
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
		LOG.debug("Start of: Vote.execute");
		String[] args = e.getMessage().getContent().split(" ");

		if (!vm.isVoteActive()) {
			if (args.length > 1) {
				LOG.debug("Enough parameters to interpret command: " + args.length);
				String topic = "";
				for (int i = 1; i < args.length; i++) {
					topic += args[i] + " ";
				}

				vm.startVote(topic, e.getAuthor());

				ms.sendMessage(e.getChannel(),
						"Vote started!\n\nTopic: " + topic + "\nYes ($yes) or No ($no).\n\nType $end to end the vote.");
				LOG.info(e.getAuthor() + " started a vote with the topic " + topic);
			} else {
				ms.sendMessage(e.getChannel(), "Give a topic to vote on.\n$vote <topic>");
				LOG.debug(e.getAuthor() + " tried to start a vote but did not provide a vote title");
			}
		} else {
			ms.sendMessage(e.getChannel(), "There is already a vote going on.\n\nTopic: " + vm.getVoteTopic()
					+ "\n\nType $end to end the vote.");
			LOG.debug(e.getAuthor() + " tired to start a vote, although there is already an active vote");
		}
		LOG.debug("End of: Vote.execute");
	}
}
