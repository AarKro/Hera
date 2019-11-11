package hera.entity.persistence;

import hera.entity.mapped.Guild;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "guild")
public class GuildPO {

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
