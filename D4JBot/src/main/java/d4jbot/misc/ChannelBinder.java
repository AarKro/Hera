package d4jbot.misc;

import d4jbots.enums.BoundChannel;
import sx.blah.discord.handle.obj.IChannel;

public class ChannelBinder {

	// default constructor
	public ChannelBinder() { }
	
	public static void bindChannel(IChannel channel) {
		BoundChannel.BOUND_CHANNEL.setBoundChannel(channel);
		MessageSender.sendMessage(channel, true, "Output-channel bound to: " + channel.mention());
	}
}
