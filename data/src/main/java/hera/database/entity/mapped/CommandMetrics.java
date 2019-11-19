package hera.database.entity.mapped;

import hera.database.entity.persistence.CommandMetricsPO;

import java.util.Date;

public class CommandMetrics implements IMappedEntity<CommandMetricsPO> {

	public static final String NAME = "CommandMetrics";

	private int command;

	private Long guild;

	private Long user;

	private int callCount;

	private Date date;

	public CommandMetrics() {
	}

	public CommandMetrics(int command, Long guild, Long user, int callCount, Date date) {
		this.command = command;
		this.guild = guild;
		this.user = user;
		this.callCount = callCount;
		this.date = date;
	}

	public CommandMetricsPO mapToPO() {
		return new CommandMetricsPO(
				this.command,
				this.guild,
				this.user,
				this.callCount,
				this.date
		);
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public int getCallCount() {
		return callCount;
	}

	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
