package hera.enums;

import hera.events.*;
import hera.events.commands.*;


public enum BotCommands {
	ALIAS("alias", AbstractCommand.getInstance(Alias.class.getName())),
	BEGONE("begone", AbstractCommand.getInstance(Begone.class.getName())),
	BIND("bind", AbstractCommand.getInstance(Bind.class.getName())),
	CLEAR("clear", AbstractCommand.getInstance(Clear.class.getName())),
	COMPCHANNEL("compChannel", AbstractCommand.getInstance(CompChannel.class.getName())),
	DELETEMESSAGES("deleteMessages", AbstractCommand.getInstance(DeleteMessages.class.getName())),
	END("end", AbstractCommand.getInstance(End.class.getName())),
	FLIP("flip", AbstractCommand.getInstance(Flip.class.getName())),
	HELP("help", AbstractCommand.getInstance(Help.class.getName())),
	JOIN("join", AbstractCommand.getInstance(Join.class.getName())),
	LEAVE("leave", AbstractCommand.getInstance(Leave.class.getName())),
	LOOPQUEUE("lq", AbstractCommand.getInstance(LoopQueue.class.getName())),
	MESSAGEOFTHEDAY("motd", AbstractCommand.getInstance(MessageOfTheDay.class.getName())),
	MOVE("move", AbstractCommand.getInstance(Move.class.getName())),
	NO("no", AbstractCommand.getInstance(No.class.getName())),
	NOWPLAYING("np", AbstractCommand.getInstance(NowPlaying.class.getName())),
	PAUSE("pause", AbstractCommand.getInstance(Pause.class.getName())),
	PLAY("play", AbstractCommand.getInstance(Play.class.getName())),
	PLAYLISTS("playlists", AbstractCommand.getInstance(Playlists.class.getName())),
	PREFIX("prefix", AbstractCommand.getInstance(Prefix.class.getName())),
	QUEUE("queue", AbstractCommand.getInstance(Queue.class.getName())),
	REMOVE("remove", AbstractCommand.getInstance(Remove.class.getName())),
	REPLAY("replay", AbstractCommand.getInstance(Replay.class.getName())),
	REPORT("report", AbstractCommand.getInstance(Report.class.getName())),
	RESUME("resume", AbstractCommand.getInstance(Resume.class.getName())),
	SAVETOPLAYLIST("savetoplaylist", AbstractCommand.getInstance(SaveToPlaylist.class.getName())),
	SHAME("shame", AbstractCommand.getInstance(Shame.class.getName())),
	SKIP("skip", AbstractCommand.getInstance(Skip.class.getName())),
	STARTPLAYLIST("startplaylist", AbstractCommand.getInstance(StartPlaylist.class.getName())),
	TEAMS("teams", AbstractCommand.getInstance(Teams.class.getName())),
	VERSION("version", AbstractCommand.getInstance(Version.class.getName())),
	VOLUME("volume", AbstractCommand.getInstance(Volume.class.getName())),
	VOTE("vote", AbstractCommand.getInstance(Vote.class.getName())),
	YES("yes", AbstractCommand.getInstance(Yes.class.getName()));
	
	private String commandName;
	private AbstractCommand commandInstance;
	
	public AbstractCommand getCommandInstance() {
		return commandInstance;
	}

	public void setCommandInstance(AbstractCommand commandInstance) {
		this.commandInstance = commandInstance;
	}

	// constructor
	private BotCommands(String commandName, AbstractCommand command) {
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
