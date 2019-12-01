package hera.database.entities.mapped;

import hera.database.entities.persistence.DefaultRolePO;

public class DefaultRole implements IMappedEntity<DefaultRolePO> {

	public static final String NAME = "DefaultRole";

	private Long guild;

	private int role;

	public DefaultRole() {
	}

	public DefaultRole(Long guild, int role) {
		this.guild = guild;
		this.role = role;
	}

	public DefaultRolePO mapToPO() {
		return new DefaultRolePO(
				this.guild,
				this.role
		);
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
