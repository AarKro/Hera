package hera.core;

import hera.database.entities.mapped.Command;

import java.util.List;

import static hera.store.DataStore.STORE;

public class HeraUtil {
	public static List<Command> getCommandsFromMessage(String message, String prefix) {
		// Add alias stuff here
		// message is a complete discord command. (prefix + command + parameters)
		return STORE.commands().forName(message.split(" ")[0].substring(prefix.length()));
	}
}
