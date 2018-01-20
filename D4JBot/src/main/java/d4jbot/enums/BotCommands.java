package d4jbot.enums;

public enum BotCommands {
	BEGONE("begone"), BIND("bind"), END("end"), FLIP("flip") ,JOIN("join"), LEAVE("leave"), LQ("lq"), MOTD("motd"), NO("no"), NP("np"), PLAY("play"), QUEUE("queue"), REPORT("report"),
	TEAMS("teams"), VERSION("version"), VOLUME("volume"), VOTE("vote"), YES("yes");
	
	private String commandName;
	
	// constructor
	private BotCommands(String commandName) {
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
