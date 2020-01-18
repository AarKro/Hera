package hera.database.entities;

import hera.database.types.GlobalSettingKey;

import javax.persistence.*;

@Entity
@Table(name = "global_setting")
public class GlobalSetting implements PersistenceEntity {

	public static final String ENTITY_NAME = "GlobalSetting";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
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
