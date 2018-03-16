package d4jbot.events;

import d4jbot.misc.MessageSender;
import d4jbot.misc.VoteManager;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class End implements Command {
	private static End instance;

	public static End getInstance() {
		if (instance == null)
			instance = new End();
		return instance;
	}

	private MessageSender ms;
	private VoteManager vm;

	// default constructor
	private End() {
		this.ms = MessageSender.getInstance();
		this.vm = VoteManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		if (vm.isVoteActive()) {
			if (e.getAuthor() == vm.getVoteOrganiser()
					|| e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
				ms.sendMessage(e.getChannel(),
						"Vote has ended!\n\nTopic: " + vm.getVoteTopic() + "\nYes: " + vm.getCountYes() + "\nNo: "
								+ vm.getCountNo() + "\n\nVote was started by " + vm.getVoteOrganiser().mention()
								+ "\nVote was ended by " + e.getAuthor().mention());
				vm.resetVote();
			} else {
				ms.sendMessage(e.getChannel(),
						"You must have started the vote or be an Administrator to end the vote.");
			}
		} else {
			ms.sendMessage(e.getChannel(), "There is no active vote to vote on.\nType $vote <topic> to start a vote.");
		}
	}
}
