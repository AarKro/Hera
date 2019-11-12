package hera.database.entity.mapped;

public class BindingType implements IMappedEntity {

	public static final String NAME = "BindingType";

	private int id;

	private String type;

	public BindingType() {
	}

	public BindingType(int id, String type) {
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
