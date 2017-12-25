package d4jbot.events;

import d4jbot.misc.MessageSender;
import d4jbot.misc.VoteManager;
import d4jbots.enums.BotPrefix;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

public class End {

	private MessageSender ms;
	private VoteManager vm;
	
	// default constructor
	public End() { }
	
	// constructor
	public End(MessageSender ms, VoteManager vm) {
		this.ms = ms;
		this.vm = vm;
	}
			
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if(e.getMessage().getContent().startsWith(BotPrefix.BOT_PREFIX.getBotPrefix() + "end")) {
			if(vm.isVoteActive()) {
				if(e.getAuthor() == vm.getVoteOrganiser() || e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
					ms.sendMessage(e.getChannel(), true, "Vote has ended!\n\nTopic: " + vm.getVoteTopic() + "\nYes: " + vm.getCountYes() + "\nNo: " + vm.getCountNo() + 
							"\n\nVote was started by " + vm.getVoteOrganiser().mention() + "\nVote was ended by " + e.getAuthor().mention());
					vm.resetVote();
				} else {
					ms.sendMessage(e.getChannel(), true, "You must have started the vote or be an Administrator to end the vote.");
				}
			} else {
				ms.sendMessage(e.getChannel(), true, "There is no active vote to vote on.\nType $vote <topic> to start a vote.");
			}
		}
	}
}
