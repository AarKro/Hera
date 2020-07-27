package hera.store.unit;

import hera.database.entities.ConfigFlagType;
import hera.database.types.ConfigFlagName;

import java.util.Collections;
import java.util.List;

public class ConfigFlagTypeAccessUnit extends StorageAccessUnit<ConfigFlagType> {
	public ConfigFlagTypeAccessUnit() {
		super(ConfigFlagType.class);
	}

	public List<ConfigFlagType> forName(ConfigFlagName name) {
		return get(Collections.singletonMap("name", name));
	}

	public List<ConfigFlagType> defaults() {
		return get(Collections.singletonMap("isDefault", true));
	}
}
