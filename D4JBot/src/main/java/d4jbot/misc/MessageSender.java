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

	public void sendMessage(IChannel defaultChannel, String message) {
		if(defaultChannel != null) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.withColor(Color.ORANGE);
			eb.appendDesc(message);
			
			EmbedObject em = eb.build();
			
			try {
				defaultChannel.sendMessage(em);
			} catch (DiscordException e) {
				System.err.println("Message could not be sent with error: ");
				e.printStackTrace();
			}
		} else System.out.println("Channel binding is missing!");
	}
}
