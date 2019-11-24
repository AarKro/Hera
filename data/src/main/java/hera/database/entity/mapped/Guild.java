package hera.database.entity.mapped;

import hera.database.entity.persistence.GuildPO;

public class Guild implements IMappedEntity<GuildPO> {

	public static final String NAME = "Guild";

	private Long snowflake;

	public Guild() {
	}

	public GuildPO mapToPO() {
		return new GuildPO(
				this.snowflake
		);
	}

	public Guild(Long snowflake) {
		this.snowflake = snowflake;
	}

	public Long getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Long snowflake) {
		this.snowflake = snowflake;
	}
}
