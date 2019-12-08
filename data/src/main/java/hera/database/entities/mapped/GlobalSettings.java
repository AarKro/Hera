package hera.database.entities.mapped;

import hera.database.entities.persistence.GlobalSettingsPO;
import hera.database.types.GlobalSettingKey;

public class GlobalSettings implements IMappedEntity<GlobalSettingsPO> {

	public static final String NAME = "GlobalSettings";

	private int id;

	private GlobalSettingKey key;

	private String value;

	public GlobalSettings() {
	}

	public GlobalSettings(GlobalSettingKey key, String value) {
		this.key = key;
		this.value = value;
	}

	public GlobalSettings(int id, GlobalSettingKey key, String value) {
		this.id = id;
		this.key = key;
		this.value = value;
	}

	public GlobalSettingsPO mapToPO() {
		return new GlobalSettingsPO(
				this.key,
				this.value
		);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GlobalSettingKey getKey() {
		return key;
	}

	public void setKey(GlobalSettingKey key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
