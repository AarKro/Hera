package hera.database.entity.mapped;

public class User implements IMappedEntity {

	public static final String NAME = "User";

	private Long snowflake;

	public User() {
	}

	public User(Long snowflake) {
		this.snowflake = snowflake;
	}

	public Long getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Long snowflake) {
		this.snowflake = snowflake;
	}
}
