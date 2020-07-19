package hera.store.unit;

import hera.database.entities.Binding;
import hera.database.entities.BindingType;
import hera.database.entities.Role;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class BindingTypeAccessUnit extends StorageAccessUnit<BindingType>{

	public BindingTypeAccessUnit() {
		super(BindingType.class);
	}

	public List<BindingType> forName(String name) {
		return get(Collections.singletonMap("type", name));
	}
}
