package hera.database.entities;

import hera.database.types.GuildSettingKey;

import javax.persistence.*;

@Entity
@Table(name = "guild_setting")
public class GuildSetting implements PersistenceEntity {

	public static final String ENTITY_NAME = "GuildSetting";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "guildFK")
	private Long guild;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private GuildSettingKey key;

	private String value;

	public GuildSetting() {
	}

	public GuildSetting(Long guild, GuildSettingKey key, String value) {
		this.guild = guild;
		this.key = key;
		this.value = value;
	}

	public GuildSetting(int id, Long guild, GuildSettingKey key, String value) {
		this.id = id;
		this.guild = guild;
		this.key = key;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
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
