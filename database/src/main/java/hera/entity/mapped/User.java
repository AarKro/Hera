package hera.entity.mapped;

public class User {

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
