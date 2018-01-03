package d4jbot.events;

import d4jbot.enums.BotPrefix;
import d4jbot.misc.MessageSender;
import d4jbot.misc.VoteManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class N {
	
	private MessageSender ms;
	private VoteManager vm;
	
	// default constructor
	public N() { }
	
	// constructor
	public N(MessageSender ms, VoteManager vm) {
		this.ms = ms;
		this.vm = vm;
	}
			
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "n")) {
			if(vm.isVoteActive()) {
				if(!vm.hasAlreadyVoted(e.getAuthor())) {
					vm.addUserToAlreadyVoted(e.getAuthor());
					vm.setCountNo(vm.getCountNo() + 1);
				} else {
					ms.sendMessage(e.getChannel(), true, "You have already voted!");
				}
			} else {
				ms.sendMessage(e.getChannel(), true, "There is no active vote to vote on.\nType $vote <topic> to start a vote.");
			}
		}
	}
}
