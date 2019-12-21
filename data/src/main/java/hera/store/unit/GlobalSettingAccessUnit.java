package hera.store.unit;

import hera.database.entities.mapped.GlobalSetting;
import hera.database.entities.persistence.GlobalSettingPO;
import hera.database.types.GlobalSettingKey;

import java.util.List;
import java.util.stream.Collectors;

public class GlobalSettingAccessUnit extends StorageAccessUnit<GlobalSettingPO, GlobalSetting>{

	public GlobalSettingAccessUnit() {
		super(GlobalSettingPO.ENTITY_NAME);
	}

	public List<GlobalSetting> forKey(GlobalSettingKey key) {
		return data.stream().filter((g) -> g.getKey().equals(key)).collect(Collectors.toList());
	}
}
