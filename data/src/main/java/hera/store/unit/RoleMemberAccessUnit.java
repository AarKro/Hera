package hera.store.unit;

import hera.database.entities.mapped.RoleMember;
import hera.database.entities.persistence.RoleMemberPO;
import hera.database.types.SnowflakeType;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMemberAccessUnit extends StorageAccessUnit<RoleMemberPO, RoleMember>{

	public RoleMemberAccessUnit() {
		super(RoleMemberPO.ENTITY_NAME);
	}

	public List<RoleMember> forRole(int role) {
		return data.stream().filter((r) -> r.getRole() == role).collect(Collectors.toList());
	}

	public List<RoleMember> forRoleAndType(int role, SnowflakeType type) {
		return data.stream().filter((r) -> r.getRole() == role && r.getSnowflakeType() == type.getValue()).collect(Collectors.toList());
	}
}
