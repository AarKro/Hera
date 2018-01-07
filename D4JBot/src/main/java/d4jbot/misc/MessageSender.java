package d4jbot.misc;

import java.awt.Color;

import d4jbot.enums.BoundChannel;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;

public class MessageSender {

	// default constructor
	public MessageSender() { }

	public void sendMessage(IChannel defaultChannel, boolean forceChannel, String message) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.withColor(Color.ORANGE);
		eb.appendDesc(message);
		
		EmbedObject em = eb.build();
		
		try {
			if(forceChannel) {
				defaultChannel.sendMessage(em);
			} else if(BoundChannel.REPORT.getBoundChannel() != null) {
				BoundChannel.REPORT.getBoundChannel().sendMessage(em);
			} else {
				em.description = "Bind me to a channel first ($bind).";
				defaultChannel.sendMessage(em);
			}
		} catch (DiscordException e) {
			System.err.println("Message could not be sent with error: ");
			e.printStackTrace();
		}
	}
}
