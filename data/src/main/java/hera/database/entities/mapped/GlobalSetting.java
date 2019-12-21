package hera.database.entities.mapped;

import hera.database.entities.persistence.GlobalSettingPO;
import hera.database.types.GlobalSettingKey;

public class GlobalSetting implements IMappedEntity<GlobalSettingPO> {

	public static final String NAME = "GlobalSetting";

	private int id;

	private GlobalSettingKey key;

	private String value;

	public GlobalSetting() {
	}

	public GlobalSetting(GlobalSettingKey key, String value) {
		this.key = key;
		this.value = value;
	}

	public GlobalSetting(int id, GlobalSettingKey key, String value) {
		this.id = id;
		this.key = key;
		this.value = value;
	}

	public GlobalSettingPO mapToPO() {
		return new GlobalSettingPO(
				this.id,
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
