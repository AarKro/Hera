package hera.store.unit;

import hera.database.entities.Role;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class RoleAccessUnit extends StorageAccessUnit<Role>{

	public RoleAccessUnit() {
		super(Role.class, Role.ENTITY_NAME);
	}

	public List<Role> forGuild(Long guild) {
		return get(Collections.singletonMap("guildFK", guild));
	}

	public List<Role> forName(String name) {
		return get(Collections.singletonMap("name", name));
	}

	public List<Role> forGuildAndName(Long guild, String name) {
		return get(new LinkedHashMap<String, Object>() {{
			put("guildFK", guild);
			put("name", name);
		}});
	}
}
