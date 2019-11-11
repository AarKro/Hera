package hera.entity.persistence;

import hera.entity.mapped.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserPO {

	public static final String ENTITY_NAME = "UserPO";

	@Id
	private Long snowflake;

	public UserPO() {
	}

	public UserPO(Long snowflake) {
		this.snowflake = snowflake;
	}

	public User mapToNonePO() {
		return new User(
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
