package hera.database.entity.mapped;

import hera.database.entity.persistence.SnowflakeTypePO;

public class SnowflakeType implements IMappedEntity<SnowflakeTypePO> {

	public static final String NAME = "SnowflakeType";

	private int id;

	private String type;

	public SnowflakeType() {
	}

	public SnowflakeType(int id, String type) {
		this.id = id;
		this.type = type;
	}

	public SnowflakeTypePO mapToPO() {
		return new SnowflakeTypePO(
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
