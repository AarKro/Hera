package hera.database.entities.persistence;

import hera.database.entities.mapped.GuildSetting;
import hera.database.types.GuildSettingKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "guild_setting")
public class GuildSettingPO implements IPersistenceEntity<GuildSetting>, Serializable {

	public static final String ENTITY_NAME = "GuildSettingPO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Long guildFK;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private GuildSettingKey key;

	private String value;

	public GuildSettingPO() {
	}

	public GuildSettingPO(int id, Long guildFK, GuildSettingKey key, String value) {
		this.id = id;
		this.guildFK = guildFK;
		this.key = key;
		this.value = value;
	}

	public GuildSetting mapToNonePO() {
		return new GuildSetting(
				this.id,
				this.guildFK,
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

	public Long getGuildFK() {
		return guildFK;
	}

	public void setGuildFK(Long guildFK) {
		this.guildFK = guildFK;
	}

	public GuildSettingKey getKey() {
		return key;
	}

	public void setKey(GuildSettingKey key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
