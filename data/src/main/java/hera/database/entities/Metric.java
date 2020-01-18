package hera.database.entities;

import hera.database.types.MetricKey;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "metric")
public class Metric implements PersistenceEntity {

	public static final String ENTITY_NAME = "Metric";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private MetricKey key;

	private Timestamp date;

	@ManyToOne
	@JoinColumn(name = "commandFK")
	private Command command;

	@Column(name = "guildFK")
	private Long guild;

	@Column(name = "userFK")
	private Long user;

	private Long value;

	private String details;

	public Metric() {
	}

	public Metric(MetricKey key, Timestamp date, Command command, Long guild, Long user, Long value, String details) {
		this.key = key;
		this.date = date;
		this.command = command;
		this.guild = guild;
		this.user = user;
		this.value = value;
		this.details = details;
	}

	public Metric(Long id, MetricKey key, Timestamp date, Command command, Long guild, Long user, Long value, String details) {
		this.id = id;
		this.key = key;
		this.date = date;
		this.command = command;
		this.guild = guild;
		this.user = user;
		this.value = value;
		this.details = details;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MetricKey getKey() {
		return key;
	}

	public void setKey(MetricKey key) {
		this.key = key;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
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
