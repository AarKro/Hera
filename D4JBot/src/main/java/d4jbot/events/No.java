package d4jbot.events;

import d4jbot.enums.BotSettings;
import d4jbot.misc.MessageSender;
import d4jbot.misc.VoteManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class No {
	
	private MessageSender ms;
	private VoteManager vm;
	
	// default constructor
	public No() { }
	
	// constructor
	public No(MessageSender ms, VoteManager vm) {
		this.ms = ms;
		this.vm = vm;
	}
			
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue() + "no")) {
			if(vm.isVoteActive()) {
				if(!vm.hasAlreadyVoted(e.getAuthor())) {
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
}
