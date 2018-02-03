package d4jbot.misc;

import java.awt.Color;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.EmbedBuilder;

public class MessageSender {
	private static MessageSender instance;
	
	public static MessageSender getInstance() {
		if (instance == null) {
			instance = new MessageSender();
		}
		return instance;
	}
	
	
	// default constructor
	private MessageSender() { }

	public void sendMessage(IChannel defaultChannel, String message) {
		if(defaultChannel != null) {
			EmbedBuilder eb = new EmbedBuilder();
			eb.withColor(Color.ORANGE);
			eb.appendDesc(message);
			
			EmbedObject em = eb.build();
			
			try {
				defaultChannel.sendMessage(em);
			} catch (Exception e) {
				System.err.println("Message could not be sent with error: ");
				e.printStackTrace();
			}
		} else System.out.println("Channel binding is missing!");
	}
}
