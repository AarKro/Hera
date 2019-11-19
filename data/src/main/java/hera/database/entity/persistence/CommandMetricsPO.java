package hera.database.entity.persistence;

import hera.database.entity.mapped.CommandMetrics;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "command_metrics")
public class CommandMetricsPO implements IPersistenceEntity<CommandMetrics>, Serializable {

	public static final String ENTITY_NAME = "CommandMetricsPO";

	@Id
	private int commandFK;

	@Id
	private Long guildFK;

	@Id
	private Long userFK;

	private int callCount;

	@Id
	@Temporal(TemporalType.DATE)
	private Date date;

	public CommandMetricsPO() {
	}

	public CommandMetricsPO(int commandFK, Long guildFK, Long userFK, int callCount, Date date) {
		this.commandFK = commandFK;
		this.guildFK = guildFK;
		this.userFK = userFK;
		this.callCount = callCount;
		this.date = date;
	}

	public CommandMetrics mapToNonePO() {
		return new CommandMetrics(
				this.commandFK,
				this.guildFK,
				this.userFK,
				this.callCount,
				this.date
		);
	}

	public int getCommandFK() {
		return commandFK;
	}

	public void setCommandFK(int commandFK) {
		this.commandFK = commandFK;
	}

	public Long getGuildFK() {
		return guildFK;
	}

	public void setGuildFK(Long guildFK) {
		this.guildFK = guildFK;
	}

	public Long getUserFK() {
		return userFK;
	}

	public void setUserFK(Long userFK) {
		this.userFK = userFK;
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
