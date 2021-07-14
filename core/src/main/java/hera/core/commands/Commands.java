package hera.core.commands;

import hera.core.commands.global.Feedback;
import hera.core.commands.global.UpdateYtToken;
import hera.core.commands.global.Uptime;
import hera.core.commands.global.Version;
import hera.core.commands.guild.*;
import hera.core.commands.miscellaneous.Flip;
import hera.core.commands.miscellaneous.Teams;
import hera.core.commands.miscellaneous.Vote;
import hera.core.commands.moderation.DeleteMessages;
import hera.core.commands.moderation.OnJoinRole;
import hera.core.commands.moderation.Report;
import hera.core.commands.music.*;
import hera.core.events.commands.ICommandHandler;
import hera.database.types.CommandName;

import java.util.HashMap;
import java.util.Map;

public class Commands {
	public static final Map<CommandName, ICommandHandler> COMMANDS = new HashMap<>();

	private Commands() {

	}

	// don't like this
	public static void initialise() {
		COMMANDS.put(CommandName.UPTIME, Uptime::execute);
		COMMANDS.put(CommandName.VERSION, Version::execute);
		COMMANDS.put(CommandName.HELP, Help::execute);
		COMMANDS.put(CommandName.DELETEMESSAGES, DeleteMessages::execute);
		COMMANDS.put(CommandName.PREFIX, Prefix::execute);
		COMMANDS.put(CommandName.JOIN, Join::execute);
		COMMANDS.put(CommandName.PLAY, Play::execute);
		COMMANDS.put(CommandName.LEAVE, Leave::execute);
		COMMANDS.put(CommandName.QUEUE, Queue::execute);
		COMMANDS.put(CommandName.SKIP, Skip::execute);
		COMMANDS.put(CommandName.LOOPQUEUE, LoopQueue::execute);
		COMMANDS.put(CommandName.NOWPLAYING, NowPlaying::execute);
		COMMANDS.put(CommandName.CLEAR, Clear::execute);
		COMMANDS.put(CommandName.RESUME, Resume::execute);
		COMMANDS.put(CommandName.PAUSE, Pause::execute);
		COMMANDS.put(CommandName.TOGGLECOMMAND, ToggleCommand::execute);
		COMMANDS.put(CommandName.VOLUME, Volume::execute);
		COMMANDS.put(CommandName.SHUFFLE, Shuffle::execute);
		COMMANDS.put(CommandName.REMOVE, Remove::execute);
		COMMANDS.put(CommandName.MOVE, Move::execute);
		COMMANDS.put(CommandName.JUMPTO, JumpTo::execute);
		COMMANDS.put(CommandName.ONJOINROLE, OnJoinRole::execute);
		COMMANDS.put(CommandName.FLIP, Flip::execute);
		COMMANDS.put(CommandName.TEAMS, Teams::execute);
		COMMANDS.put(CommandName.UPDATEYTTOKEN, UpdateYtToken::execute);
		COMMANDS.put(CommandName.VOTE, Vote::execute);
		COMMANDS.put(CommandName.BIND, Bind::execute);
		COMMANDS.put(CommandName.FEEDBACK, Feedback::execute);
		COMMANDS.put(CommandName.REPORT, Report::execute);
		COMMANDS.put(CommandName.DELETEALIAS, DeleteAlias::execute);
		COMMANDS.put(CommandName.CONFIG, Config::execute);
		COMMANDS.put(CommandName.MODULESTATUS, ModuleStatus::execute);
		COMMANDS.put(CommandName.SHOWALIAS, ShowAlias::execute);
		COMMANDS.put(CommandName.CREATEALIAS, CreateAlias::execute);
	}
}
