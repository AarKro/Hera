package d4jbot.misc;

import d4jbots.enums.BoundChannel;
import sx.blah.discord.handle.obj.IChannel;

public class ChannelBinder {

	private MessageSender ms;
	
	// default constructor
	public ChannelBinder() { }
	
	// constructor
	public ChannelBinder(MessageSender ms) {
		this.ms = ms;
	}
		
	public void bindChannel(IChannel channel) {
		BoundChannel.BOUND_CHANNEL.setBoundChannel(channel);
		ms.sendMessage(channel, true, "Output-channel bound to: " + channel.mention());
	}
}
