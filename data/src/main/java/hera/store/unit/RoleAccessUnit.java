package hera.store.unit;

import hera.database.entity.mapped.Role;
import hera.database.entity.persistence.RolePO;

import java.util.List;
import java.util.stream.Collectors;

public class RoleAccessUnit extends StorageAccessUnit<RolePO, Role>{

	public RoleAccessUnit() {
		super(RolePO.ENTITY_NAME);
	}

	public List<Role> forGuild(Long guild) {
		return data.stream().filter((g) -> g.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public List<Role> forName(String name) {
		return data.stream().filter((g) -> g.getName().equals(name)).collect(Collectors.toList());
	}

	public List<Role> forGuildAndName(Long guild, String name) {
		return data.stream().filter((g) -> g.getGuild().equals(guild) && g.getName().equals(name)).collect(Collectors.toList());
	}
}
