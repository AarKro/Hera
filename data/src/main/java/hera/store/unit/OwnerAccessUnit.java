package hera.store.unit;

import hera.database.entities.Owner;

import java.util.Collections;
import java.util.List;

public class OwnerAccessUnit extends StorageAccessUnit<Owner>{

	public OwnerAccessUnit() {
		super(Owner.class, Owner.ENTITY_NAME);
	}

	public List<Owner> forUser(Long user) {
		return get(Collections.singletonMap("user", user));
	}
}
