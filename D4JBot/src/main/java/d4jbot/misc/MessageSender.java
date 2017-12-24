package d4jbot.misc;

import d4jbots.enums.BoundChannel;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;

public class MessageSender {

	// default constructor
	public MessageSender() { }

	public static void sendMessage(IChannel defaultChannel, boolean forceChannel, String message) {
		try {
			if(forceChannel) {
				defaultChannel.sendMessage(message);
			} else if(BoundChannel.BOUND_CHANNEL.getBoundChannel() != null) {
				BoundChannel.BOUND_CHANNEL.getBoundChannel().sendMessage(message);
			} else {
				defaultChannel.sendMessage("Bind me to a channel first ($bind).");
			}
		} catch (DiscordException e) {
			System.err.println("Message could not be sent with error: ");
			e.printStackTrace();
		}
	}
}
