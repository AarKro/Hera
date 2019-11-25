package hera.store.unit;

import hera.database.entity.mapped.GlobalSettings;
import hera.database.entity.persistence.GlobalSettingsPO;

import java.util.List;
import java.util.stream.Collectors;

public class GlobalSettingsAccessUnit extends StorageAccessUnit<GlobalSettingsPO, GlobalSettings>{

	public GlobalSettingsAccessUnit() {
		super(GlobalSettingsPO.ENTITY_NAME);
	}

	public List<GlobalSettings> forName(String name) {
		return data.stream().filter((g) -> g.getName().equals(name)).collect(Collectors.toList());
	}
}
