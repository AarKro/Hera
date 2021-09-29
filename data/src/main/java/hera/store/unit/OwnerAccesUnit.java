package hera.store.unit;

import hera.database.entities.Owner;

import java.util.LinkedHashMap;

public class OwnerAccesUnit extends StorageAccessUnit<Owner>{

	public OwnerAccesUnit() {
		super(Owner.class);
	}

	public Boolean isOwner(Long memberId) {
		return !get(new LinkedHashMap<String, Object>() {{
			put("id", memberId);
		}}).isEmpty();
	}
}
