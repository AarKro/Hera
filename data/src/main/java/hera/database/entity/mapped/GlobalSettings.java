package hera.database.entity.mapped;

import hera.database.entity.persistence.GlobalSettingsPO;

public class GlobalSettings implements IMappedEntity<GlobalSettingsPO> {

	public static final String NAME = "GlobalSettings";

	private int id;

	private String name;

	private String value;

	public GlobalSettings() {
	}

	public GlobalSettings(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public GlobalSettings(int id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public GlobalSettingsPO mapToPO() {
		return new GlobalSettingsPO(
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
