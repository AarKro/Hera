package hera.entity.mapped;

public class DefaultRole {

	private Long guild;

	private int role;

	public DefaultRole() {
	}

	public DefaultRole(Long guild, int role) {
		this.guild = guild;
		this.role = role;
	}

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
