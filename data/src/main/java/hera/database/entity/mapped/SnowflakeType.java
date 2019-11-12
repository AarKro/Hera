package hera.database.entity.mapped;

public class SnowflakeType implements IMappedEntity {

	public static final String NAME = "SnowflakeType";

	private int id;

	private String type;

	public SnowflakeType() {
	}

	public SnowflakeType(int id, String type) {
		this.id = id;
		this.type = type;
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
