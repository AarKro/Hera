package hera.entity.persistence;

import hera.entity.mapped.DefaultRole;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "default_role")
public class DefaultRolePO {

	public static final String ENTITY_NAME = "DefaultRolePO";

	@Id
	private Long guildFK;

	@Id
	private int roleFK;

	public DefaultRolePO() {
	}

	public DefaultRolePO(Long guildFK, int roleFK) {
		this.guildFK = guildFK;
		this.roleFK = roleFK;
	}

	public DefaultRole mapToNonePO() {
		return new DefaultRole(
				this.guildFK,
				this.roleFK
		);
	}

	public Long getGuildFK() {
		return guildFK;
	}

	public void setGuildFK(Long guildFK) {
		this.guildFK = guildFK;
	}

	public int getRoleFK() {
		return roleFK;
	}

	public void setRoleFK(int roleFK) {
		this.roleFK = roleFK;
	}
}
