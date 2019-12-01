package hera.database.entities.persistence;

import hera.database.entities.mapped.GuildSettings;
import hera.database.types.GuildSettingKey;

import javax.persistence.*;

@Entity
@Table(name = "guild_settings")
public class GuildSettingsPO implements IPersistenceEntity<GuildSettings> {

	public static final String ENTITY_NAME = "GuildSettingsPO";

	@Id
	@GeneratedValue
	private int id;

	private Long guildFK;

	@Enumerated(EnumType.STRING)
	private GuildSettingKey key;

	private String value;

	public GuildSettingsPO() {
	}

	public GuildSettingsPO(Long guildFK, GuildSettingKey key, String value) {
		this.guildFK = guildFK;
		this.key = key;
		this.value = value;
	}

	public GuildSettings mapToNonePO() {
		return new GuildSettings(
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
