package hera.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User implements PersistenceEntity {

	public static final String ENTITY_NAME = "User";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Long snowflake;

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
