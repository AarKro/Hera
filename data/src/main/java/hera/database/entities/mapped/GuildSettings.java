package hera.database.entities.mapped;

import hera.database.entities.persistence.GuildSettingsPO;
import hera.database.types.GuildSettingKey;

public class GuildSettings implements IMappedEntity<GuildSettingsPO> {

	public static final String NAME = "GuildSettings";

	private int id;

	private Long guild;

	private GuildSettingKey key;

	private String value;

	public GuildSettings() {
	}

	public GuildSettings(Long guild, GuildSettingKey key, String value) {
		this.guild = guild;
		this.key = key;
		this.value = value;
	}

	public GuildSettings(int id, Long guild, GuildSettingKey key, String value) {
		this.id = id;
		this.guild = guild;
		this.key = key;
		this.value = value;
	}

	public GuildSettingsPO mapToPO() {
		return new GuildSettingsPO(
				this.id,
				this.guild,
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
