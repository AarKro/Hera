package hera.store.unit;

import hera.database.entities.DefaultRole;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultRoleAccessUnit extends StorageAccessUnit<DefaultRole>{

	public DefaultRoleAccessUnit() {
		super(DefaultRole.class, DefaultRole.ENTITY_NAME);
	}

	public List<DefaultRole> forGuild(Long guild) {
		return get(Collections.singletonMap("guildFK", guild));
	}

	public List<DefaultRole> forRole(int role) {
		return get(Collections.singletonMap("roleFK", role));
	}
}
