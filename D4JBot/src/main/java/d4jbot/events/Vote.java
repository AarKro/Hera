package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import d4jbot.misc.VoteManager;
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
			
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "vote")) {
			String[] args = e.getMessage().getContent().split(" ");
			
			if(!vm.isVoteActive()) {
				if(args.length > 1) {
					String topic = "";
					for(int i = 1; i < args.length; i++) {
						topic += args[i] + " ";
					}
					
					vm.startVote(topic, e.getAuthor());
					
					ms.sendMessage(e.getChannel(), "Vote started!\n\nTopic: " + topic + "\nYes ($yes) or No ($no).\n\nType $end to end the vote.");
				} else {
					ms.sendMessage(e.getChannel(), "Give a topic to vote on.\n$vote <topic>");
				}
			} else {
				ms.sendMessage(e.getChannel(), "There is already a vote going on.\n\nTopic: " + vm.getVoteTopic() + "\n\nType $end to end the vote.");
			}
		}
	}
}
