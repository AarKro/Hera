package hera.database.entity.persistence;

import hera.database.entity.mapped.BindingType;

import javax.persistence.*;

@Entity
@Table(name = "binding_type")
public class BindingTypePO implements IPersistenceEntity<BindingType> {

	public static final String ENTITY_NAME = "BindingTypePO";

	@Id
	@GeneratedValue
	private int id;

	private String type;

	public BindingTypePO() {
	}

	public BindingTypePO(String type) {
		this.type = type;
	}

	public BindingType mapToNonePO() {
		return new BindingType(
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
