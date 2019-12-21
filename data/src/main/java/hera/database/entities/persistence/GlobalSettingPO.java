package hera.database.entities.persistence;

import hera.database.entities.mapped.GlobalSetting;
import hera.database.types.GlobalSettingKey;

import javax.persistence.*;

@Entity
@Table(name = "global_settings")
public class GlobalSettingPO implements IPersistenceEntity<GlobalSetting> {

	public static final String ENTITY_NAME = "GlobalSettingsPO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private GlobalSettingKey key;

	private String value;

	public GlobalSettingPO() {
	}

	public GlobalSettingPO(int id, GlobalSettingKey key, String value) {
		this.id = id;
		this.key = key;
		this.value = value;
	}

	public GlobalSetting mapToNonePO() {
		return new GlobalSetting(
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
