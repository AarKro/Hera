package hera.database.entity.mapped;

import hera.database.entity.persistence.BindingTypePO;

public class BindingType implements IMappedEntity<BindingTypePO> {

	public static final String NAME = "BindingType";

	private int id;

	private String type;

	public BindingType() {
	}

	public BindingType(String type) {
		this.type = type;
	}

	public BindingType(int id, String type) {
		this.id = id;
		this.type = type;
	}

	public BindingTypePO mapToPO() {
		return new BindingTypePO(
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
