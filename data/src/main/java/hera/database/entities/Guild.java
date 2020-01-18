package hera.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "guild")
public class Guild implements PersistenceEntity {

	public static final String ENTITY_NAME = "Guild";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Long snowflake;

	public Guild() {
	}

	public Guild(Long snowflake) {
		this.snowflake = snowflake;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Long snowflake) {
		this.snowflake = snowflake;
	}
}
