package hera.database.entities.mapped;

import hera.database.entities.persistence.MetricPO;
import hera.database.types.MetricKey;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Metric implements IMappedEntity<MetricPO> {

	public static final String NAME = "CommandMetrics";

	private Integer id;

	private MetricKey key;

	private LocalDateTime date;

	private Integer command;

	private Long guild;

	private Long user;

	private Long value;

	private String details;

	public Metric() {
	}

	public Metric(Integer id, MetricKey key, LocalDateTime date, Integer command, Long guild, Long user, Long value, String details) {
		this.id = id;
		this.key = key;
		this.date = date;
		this.command = command;
		this.guild = guild;
		this.user = user;
		this.value = value;
		this.details = details;
	}

	public Metric(MetricKey key, LocalDateTime date, Integer command, Long guild, Long user, Long value, String details) {
		this.key = key;
		this.date = date;
		this.command = command;
		this.guild = guild;
		this.user = user;
		this.value = value;
		this.details = details;
	}

	public MetricPO mapToPO() {
		return new MetricPO(
				this.id,
				this.key,
				Timestamp.valueOf(this.date),
				this.command,
				this.guild,
				this.user,
				this.value,
				this.details
		);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MetricKey getKey() {
		return key;
	}

	public void setKey(MetricKey key) {
		this.key = key;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getCommand() {
		return command;
	}

	public void setCommand(Integer command) {
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

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
