package hera.store.unit;

import hera.database.entities.mapped.GlobalSettings;
import hera.database.entities.persistence.GlobalSettingsPO;
import hera.database.types.GlobalSettingKey;

import java.util.List;
import java.util.stream.Collectors;

public class GlobalSettingsAccessUnit extends StorageAccessUnit<GlobalSettingsPO, GlobalSettings>{

	public GlobalSettingsAccessUnit() {
		super(GlobalSettingsPO.ENTITY_NAME);
	}

	public List<GlobalSettings> forKey(GlobalSettingKey key) {
		return data.stream().filter((g) -> g.getKey().equals(key)).collect(Collectors.toList());
	}
}
