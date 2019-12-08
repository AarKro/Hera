package hera.database.entities.persistence;

import hera.database.entities.mapped.Guild;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "guild")
public class GuildPO implements IPersistenceEntity<Guild> {

	public static final String ENTITY_NAME = "GuildPO";

	@Id
	private Long snowflake;

	public GuildPO() {
	}

	public GuildPO(Long snowflake) {
		this.snowflake = snowflake;
	}

	public Guild mapToNonePO() {
		return new Guild(
				this.snowflake
		);
	}

	public Long getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Long snowflake) {
		this.snowflake = snowflake;
	}
}
