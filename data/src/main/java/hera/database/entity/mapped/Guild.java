package hera.database.entity.mapped;

public class Guild implements IMappedEntity {

	public static final String NAME = "Guild";

	private Long snowflake;

	public Guild() {
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
