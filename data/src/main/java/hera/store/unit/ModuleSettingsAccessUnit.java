package hera.store.unit;

import hera.database.entity.mapped.ModuleSettings;
import hera.database.entity.persistence.ModuleSettingsPO;

import java.util.List;
import java.util.stream.Collectors;

public class ModuleSettingsAccessUnit extends StorageAccessUnit<ModuleSettingsPO, ModuleSettings>{

	public ModuleSettingsAccessUnit() {
		super(ModuleSettingsPO.ENTITY_NAME);
	}

	public List<ModuleSettings> forGuild(Long guild) {
		return data.stream().filter((m) -> m.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public List<ModuleSettings> forCommand(int command) {
		return data.stream().filter((m) -> m.getCommand() == command).collect(Collectors.toList());
	}

	public List<ModuleSettings> forModule(Long guild, int command) {
		return data.stream().filter((m) -> m.getGuild().equals(guild) && m.getCommand() == command).collect(Collectors.toList());
	}
}
