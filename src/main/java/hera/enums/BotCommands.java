package hera.enums;

import hera.events.*;
import hera.events.commands.*;


public enum BotCommands {
	ALIAS("alias", Command.getInstance(Alias.class.getName())),
	BEGONE("begone", Command.getInstance(Begone.class.getName())),
	BIND("bind", Command.getInstance(Bind.class.getName())),
	CLEAR("clear", Command.getInstance(Clear.class.getName())),
	COMPCHANNEL("compChannel", Command.getInstance(CompChannel.class.getName())),
	DELETEMESSAGES("deleteMessages", Command.getInstance(DeleteMessages.class.getName())),
	END("end", Command.getInstance(End.class.getName())),
	FLIP("flip", Command.getInstance(Flip.class.getName())),
	HELP("help", Command.getInstance(Help.class.getName())),
	JOIN("join", Command.getInstance(Join.class.getName())),
	LEAVE("leave", Command.getInstance(Leave.class.getName())),
	LOOPQUEUE("lq", Command.getInstance(LoopQueue.class.getName())),
	MESSAGEOFTHEDAY("motd", Command.getInstance(MessageOfTheDay.class.getName())),
	MOVE("move", Command.getInstance(Move.class.getName())),
	NO("no", Command.getInstance(No.class.getName())),
	NOWPLAYING("np", Command.getInstance(NowPlaying.class.getName())),
	PAUSE("pause", Command.getInstance(Pause.class.getName())),
	PLAY("play", Command.getInstance(Play.class.getName())),
	PLAYLISTS("playlists", Command.getInstance(Playlists.class.getName())),
	PREFIX("prefix", Command.getInstance(Prefix.class.getName())),
	QUEUE("queue", Command.getInstance(Queue.class.getName())),
	REMOVE("remove", Command.getInstance(Remove.class.getName())),
	REPLAY("replay", Command.getInstance(Replay.class.getName())),
	REPORT("report", Command.getInstance(Report.class.getName())),
	RESUME("resume", Command.getInstance(Resume.class.getName())),
	SAVETOPLAYLIST("savetoplaylist", Command.getInstance(SaveToPlaylist.class.getName())),
	SHAME("shame", Command.getInstance(Shame.class.getName())),
	SKIP("skip", Command.getInstance(Skip.class.getName())),
	STARTPLAYLIST("startplaylist", Command.getInstance(StartPlaylist.class.getName())),
	TEAMS("teams", Command.getInstance(Teams.class.getName())),
	VERSION("version", Command.getInstance(Version.class.getName())),
	VOLUME("volume", Command.getInstance(Volume.class.getName())),
	VOTE("vote", Command.getInstance(Vote.class.getName())),
	YES("yes", Command.getInstance(Yes.class.getName()));
	
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
