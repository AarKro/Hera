package hera.database.entities.persistence;

import hera.database.entities.mapped.Metric;
import hera.database.types.MetricKey;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "metric")
public class MetricPO implements IPersistenceEntity<Metric>, Serializable {

	public static final String ENTITY_NAME = "MetricPO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private MetricKey key;

	private Timestamp date;

	private Integer commandFK;

	private Long guildFK;

	private Long userFK;

	private Long value;

	private String details;

	public MetricPO() {
	}

	public MetricPO(Integer id, MetricKey key, Timestamp date, Integer commandFK, Long guildFK, Long userFK, Long value, String details) {
		this.id = id;
		this.key = key;
		this.date = date;
		this.commandFK = commandFK;
		this.guildFK = guildFK;
		this.userFK = userFK;
		this.value = value;
		this.details = details;
	}

	public Metric mapToNonePO() {
		return new Metric(
				this.id,
				this.key,
				this.date.toLocalDateTime(),
				this.commandFK,
				this.guildFK,
				this.userFK,
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

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Integer getCommandFK() {
		return commandFK;
	}

	public void setCommandFK(Integer commandFK) {
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
