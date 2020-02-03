package hera.store.unit;

import hera.database.entities.Role;
import hera.database.entities.RoleMember;
import hera.database.types.SnowflakeType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class RoleMemberAccessUnit extends StorageAccessUnit<RoleMember>{

	public RoleMemberAccessUnit() {
		super(RoleMember.class, RoleMember.ENTITY_NAME);
	}

	public List<RoleMember> forRole(Role role) {
		return get(Collections.singletonMap("role", role));
	}

	public List<RoleMember> forRoleAndType(Long role, SnowflakeType type) {
		return get(new LinkedHashMap<String, Object>() {{
			put("role", role);
			put("snowflakeType", type);
		}});
	}
}
