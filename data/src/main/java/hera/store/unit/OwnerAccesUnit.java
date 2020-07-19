package hera.store.unit;

import hera.database.entities.Alias;
import hera.database.entities.Owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

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
