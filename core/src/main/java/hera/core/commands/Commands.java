package hera.core.commands;

import hera.database.types.CommandName;

import java.util.HashMap;
import java.util.Map;

public class Commands {
	public static final Map<CommandName, Command> COMMANDS = new HashMap<>();

	private Commands() {

	}

	// don't like this
	public static void initialise() {
		COMMANDS.put(CommandName.UPTIME, Uptime::execute);
		COMMANDS.put(CommandName.VERSION, Version::execute);
		COMMANDS.put(CommandName.HELP, Help::execute);
		COMMANDS.put(CommandName.DELETEMESSAGES, DeleteMessages::execute);
		COMMANDS.put(CommandName.ALIAS, Alias::execute);
		COMMANDS.put(CommandName.PREFIX, Prefix::execute);
		COMMANDS.put(CommandName.JOIN, Join::execute);
		COMMANDS.put(CommandName.PLAY, Play::execute);
		COMMANDS.put(CommandName.LEAVE, Leave::execute);
		COMMANDS.put(CommandName.QUEUE, Queue::execute);
		COMMANDS.put(CommandName.SKIP, Skip::execute);
		COMMANDS.put(CommandName.LOOPQUEUE, LoopQueue::execute);
		COMMANDS.put(CommandName.NOWPLAYING, NowPlaying::execute);
	}
}
