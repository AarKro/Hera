package hera.database.entity.persistence;

import hera.database.entity.mapped.SnowflakeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "snowflake_type")
public class SnowflakeTypePO implements IPersistenceEntity<SnowflakeType> {

	public static final String ENTITY_NAME = "SnowflakeTypePO";

	@Id
	@GeneratedValue
	private int id;

	private String type;

	public SnowflakeTypePO() {
	}

	public SnowflakeTypePO(String type) {
		this.type = type;
	}

	public SnowflakeType mapToNonePO() {
		return new SnowflakeType(
				this.id,
				this.type
		);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
