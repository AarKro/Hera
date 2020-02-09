package hera.store.unit;

import hera.database.entities.DefaultRole;
import hera.database.entities.Role;

import java.util.Collections;
import java.util.List;

public class DefaultRoleAccessUnit extends StorageAccessUnit<DefaultRole>{

	public DefaultRoleAccessUnit() {
		super(DefaultRole.class);
	}

	public List<DefaultRole> forGuild(Long guild) {
		return get(Collections.singletonMap("guild", guild));
	}

	public List<DefaultRole> forRole(Role role) {
		return get(Collections.singletonMap("role", role));
	}
}
