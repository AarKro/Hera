package d4jbot.events;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import d4jbot.enums.BotCommands;
import d4jbot.enums.BotSettings;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class HandleMessageRecievedEvent {

	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent e) {
		if (e.getMessage().getContent().startsWith(BotSettings.BOT_PREFIX.getPropertyValue())) {

			String message = e.getMessage().getContent().substring(BotSettings.BOT_PREFIX.getPropertyValue().length());
			HashMap<String, Command> hashmap = convertEnumToHashmap();
			String command = message.split(" ")[0];
			if (hashmap.containsKey(command)) {
				hashmap.get(command).onMessageReceivedEvent(e);
			}
			
		}
	}

	private HashMap<String, Command> convertEnumToHashmap() {
		
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
		
		return hashmap;
	}
}
