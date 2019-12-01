package hera.database.entities.mapped;

import hera.database.entities.persistence.RoleMemberPO;

public class RoleMember implements IMappedEntity<RoleMemberPO> {

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

	public RoleMemberPO mapToPO() {
		return new RoleMemberPO(
				this.snowflake,
				this.role,
				this.snowflakeType
		);
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
