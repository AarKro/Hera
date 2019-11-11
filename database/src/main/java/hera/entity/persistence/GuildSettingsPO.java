package hera.entity.persistence;

import hera.entity.mapped.GuildSettings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "guild_settings")
public class GuildSettingsPO {

	public static final String ENTITY_NAME = "GuildSettingsPO";

	@Id
	@GeneratedValue
	private int id;

	private Long guildFK;

	private String name;

	private String value;

	public GuildSettingsPO() {
	}

	public GuildSettingsPO(Long guildFK, String name, String value) {
		this.guildFK = guildFK;
		this.name = name;
		this.value = value;
	}

	public GuildSettings mapToNonePO() {
		return new GuildSettings(
				this.id,
				this.guildFK,
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

	public Long getGuildFK() {
		return guildFK;
	}

	public void setGuildFK(Long guildFK) {
		this.guildFK = guildFK;
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
