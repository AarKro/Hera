package hera.store.unit;

import hera.database.entities.mapped.DefaultRole;
import hera.database.entities.persistence.DefaultRolePO;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultRoleAccessUnit extends StorageAccessUnit<DefaultRolePO, DefaultRole>{

	public DefaultRoleAccessUnit() {
		super(DefaultRolePO.ENTITY_NAME);
	}

	public List<DefaultRole> forGuild(Long guild) {
		return data.stream().filter((d) -> d.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public List<DefaultRole> forRole(int role) {
		return data.stream().filter((d) -> d.getRole() == role).collect(Collectors.toList());
	}
}
