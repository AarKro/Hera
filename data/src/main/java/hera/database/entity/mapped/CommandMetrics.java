package hera.database.entity.mapped;

public class CommandMetrics implements IMappedEntity {

	public static final String NAME = "CommandMetrics";

	private int command;

	private Long guild;

	private Long user;

	private int callCount;

	public CommandMetrics() {
	}

	public CommandMetrics(int command, Long guild, Long user, int callCount) {
		this.command = command;
		this.guild = guild;
		this.user = user;
		this.callCount = callCount;
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
}
