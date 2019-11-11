package hera.entity.mapped;

public class Guild {

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
