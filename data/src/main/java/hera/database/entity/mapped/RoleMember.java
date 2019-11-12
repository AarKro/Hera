package hera.database.entity.mapped;

public class RoleMember implements IMappedEntity {

	public static final String NAME = "RoleMember";

	private Long snowflake;

	private int role;

	private int snowflakeType;

	public RoleMember() {
	}

	public RoleMember(Long snowflake, int role, int snowflakeType) {
		this.snowflake = snowflake;
		this.role = role;
		this.snowflakeType = snowflakeType;
	}

	public Long getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Long snowflake) {
		this.snowflake = snowflake;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getSnowflakeType() {
		return snowflakeType;
	}

	public void setSnowflakeType(int snowflakeType) {
		this.snowflakeType = snowflakeType;
	}
}
