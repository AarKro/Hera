package hera.core.commands;

import hera.database.types.CommandName;

import java.util.HashMap;
import java.util.Map;

public class Commands {
	public static final Map<CommandName, Command> COMMANDS = new HashMap<>();

	// don't like this
	public static void initialise() {
		COMMANDS.put(CommandName.UPTIME, Uptime::execute);
	}
}
