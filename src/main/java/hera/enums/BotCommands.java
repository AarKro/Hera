package hera.enums;

import hera.events.*;


public enum BotCommands {
	BEGONE("begone", Begone.getInstance()),
	BIND("bind", Bind.getInstance()),
	CLEAR("clear", Clear.getInstance()),
	END("end", End.getInstance()),
	FLIP("flip", Flip.getInstance()), 
	HELP("help", Help.getInstance()),
	JOIN("join", Join.getInstance()), 
	LEAVE("leave", Leave.getInstance()), 
	LQ("lq", Lq.getInstance()), 
	MOTD("motd", Motd.getInstance()), 
	MOVE("move", Move.getInstance()),
	NO("no", No.getInstance()), 
	NP("np", Np.getInstance()), 
	PAUSE("pause", Pause.getInstance()),
	PLAY("play,p", Play.getInstance()), 
	QUEUE("queue", Queue.getInstance()),
	REMOVE("remove,rm", Remove.getInstance()),
	REPLAY("replay", Replay.getInstance()),
	REPORT("report", Report.getInstance()),
	RESUME("resume", Resume.getInstance()),
	SHAME("shame", Shame.getInstance()),
	SKIP("skip", Skip.getInstance()),
	TEAMS("teams", Teams.getInstance()), 
	VERSION("version", Version.getInstance()), 
	VOLUME("volume", Volume.getInstance()), 
	VOTE("vote", Vote.getInstance()), 
	YES("yes", Yes.getInstance());
	
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
		this.commandInstance = command;
	}

	// getters & setters
	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
}
