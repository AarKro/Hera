package hera.database.entity.persistence;

import hera.database.entity.mapped.GlobalSettings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "global_settings")
public class GlobalSettingsPO  implements IPersistenceEntity<GlobalSettings> {

	public static final String ENTITY_NAME = "GlobalSettingsPO";

	@Id
	@GeneratedValue
	private int id;

	private String name;

	private String value;

	public GlobalSettingsPO() {
	}

	public GlobalSettingsPO(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public GlobalSettings mapToNonePO() {
		return new GlobalSettings(
				this.id,
				this.name,
				this.value
		);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
