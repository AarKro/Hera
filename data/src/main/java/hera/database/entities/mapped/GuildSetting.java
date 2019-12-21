package hera.database.entities.mapped;

import hera.database.entities.persistence.GuildSettingPO;
import hera.database.types.GuildSettingKey;

public class GuildSetting implements IMappedEntity<GuildSettingPO> {

	public static final String NAME = "GuildSetting";

	private int id;

	private Long guild;

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

	public GuildSettingPO mapToPO() {
		return new GuildSettingPO(
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
