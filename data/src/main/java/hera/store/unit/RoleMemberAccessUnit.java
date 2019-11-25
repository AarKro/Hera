package hera.store.unit;

import hera.database.entity.mapped.RoleMember;
import hera.database.entity.persistence.RoleMemberPO;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMemberAccessUnit extends StorageAccessUnit<RoleMemberPO, RoleMember>{

	public RoleMemberAccessUnit() {
		super(RoleMemberPO.ENTITY_NAME);
	}

	public List<RoleMember> forRole(int role) {
		return data.stream().filter((r) -> r.getRole() == role).collect(Collectors.toList());
	}
}
