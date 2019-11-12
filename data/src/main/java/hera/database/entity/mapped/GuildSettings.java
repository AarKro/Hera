package hera.database.entity.mapped;

public class GuildSettings implements IMappedEntity {

	public static final String NAME = "GuildSettings";

	private int id;

	private Long guild;

	private String name;

	private String value;

	public GuildSettings() {
	}

	public GuildSettings(int id, Long guild, String name, String value) {
		this.id = id;
		this.guild = guild;
		this.name = name;
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
