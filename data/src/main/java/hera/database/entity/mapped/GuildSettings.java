package hera.database.entity.mapped;

import hera.database.entity.persistence.GuildSettingsPO;

public class GuildSettings implements IMappedEntity<GuildSettingsPO> {

	public static final String NAME = "GuildSettings";

	private int id;

	private Long guild;

	private String name;

	private String value;

	public GuildSettings() {
	}

	public GuildSettings(Long guild, String name, String value) {
		this.guild = guild;
		this.name = name;
		this.value = value;
	}

	public GuildSettings(int id, Long guild, String name, String value) {
		this.id = id;
		this.guild = guild;
		this.name = name;
		this.value = value;
	}

	public GuildSettingsPO mapToPO() {
		return new GuildSettingsPO(
				this.guild,
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

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
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
