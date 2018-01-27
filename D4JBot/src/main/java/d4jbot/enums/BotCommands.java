package d4jbot.enums;

import d4jbot.events.Command;

public enum BotCommands {
	BEGONE("begone", null), BIND("bind", null), END("end", null), FLIP("flip", null), JOIN("join", null), LEAVE("leave", null), LQ("lq", null),
	MOTD("motd", null), NO("no", null), NP("np", null), PLAY("play", null), QUEUE("queue", null), REPORT("report", null), TEAMS("teams", null),
	VERSION("version", null), VOLUME("volume", null), VOTE("vote", null), YES("yes", null), HELP("help", null), SKIP("skip", null), SHAME("shame", null),
	REMOVE("remove", null), PAUSE("pause", null), RESUME("resume", null), CLEAR("resume", null);
	
	private String commandName;
	private Command commandInstance;
	
	public Command getCommandInstance() {
		return commandInstance;
	}

	public void setCommandInstance(Command commandInstance) {
		this.commandInstance = commandInstance;
	}

	// constructor
	private BotCommands(String commandName, Command command) {
		this.commandName = commandName;
	}

	// getters & setters
	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
}
