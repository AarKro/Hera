package hera.events;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BotCommands;
import hera.enums.BotSettings;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class MessageReceivedEventHandler {

	private static final Logger LOG = LoggerFactory.getLogger(MessageReceivedEventHandler.class);
	
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		LOG.debug("Start of: MessageReceivedEventHandler.onMessageReceivedEvent");
		if (e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue())) {
			LOG.debug(e.getAuthor() + " has typed a message starting with Heras command prefix");
			
			String message = e.getMessage().getContent().substring(BotSettings.BOT_PREFIX.getPropertyValue().length());
			HashMap<String, Command> hashmap = convertEnumToHashmap();
			String command = message.split(" ")[0];
			if (hashmap.containsKey(command)) {
				LOG.info(e.getAuthor() + " has issued the command " + command);
				LOG.info("Starting execution of \"" + command + "\"");
				hashmap.get(command).execute(e);
				LOG.info("Ended execution of \"" + command + "\"");
			}
			
		}
		LOG.debug("End of: MessageReceivedEventHandler.onMessageReceivedEvent");
	}

	private HashMap<String, Command> convertEnumToHashmap() {
		LOG.debug("Start of: MessageReceivedEventHandler.convertEnumToHashmap");
		BotCommands[] values = BotCommands.values();
		
		HashMap<String, Command> hashmap = new HashMap<>();
		for (BotCommands value : values) {
			if (value.getCommandName().contains(",")) {
				String[] names = value.getCommandName().split(",");
				for (String name : names) {
					hashmap.put(name, value.getCommandInstance());
				}
			} else {
				hashmap.put(value.getCommandName(), value.getCommandInstance());
			}
		}
		
		LOG.debug("End of: MessageReceivedEventHandler.convertEnumToHashmap");
		return hashmap;
	}
}
