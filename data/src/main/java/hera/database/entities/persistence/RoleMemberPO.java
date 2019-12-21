package hera.database.entities.persistence;

import hera.database.entities.mapped.RoleMember;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_member")
public class RoleMemberPO implements IPersistenceEntity<RoleMember> {

	public static final String ENTITY_NAME = "RoleMemberPO";

	@Id
	private Long snowflake;

	private int roleFK;

	private int snowflakeTypeFK;

	public RoleMemberPO() {
	}

	public RoleMemberPO(Long snowflake, int roleFK, int snowflakeTypeFK) {
		this.snowflake = snowflake;
		this.roleFK = roleFK;
		this.snowflakeTypeFK = snowflakeTypeFK;
	}

	public RoleMember mapToNonePO() {
		return new RoleMember(
				this.snowflake,
				this.roleFK,
				this.snowflakeTypeFK
		);
	}

	public Long getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Long snowflake) {
		this.snowflake = snowflake;
	}

	public int getRoleFK() {
		return roleFK;
	}

	public void setRoleFK(int roleFK) {
		this.roleFK = roleFK;
	}

	public int getSnowflakeTypeFK() {
		return snowflakeTypeFK;
	}

	public void setSnowflakeTypeFK(int snowflakeTypeFK) {
		this.snowflakeTypeFK = snowflakeTypeFK;
	}
}
