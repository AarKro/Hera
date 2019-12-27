package hera.store.unit;

import hera.database.entities.mapped.ModuleSettings;
import hera.database.entities.persistence.ModuleSettingsPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ModuleSettingsAccessUnit extends StorageAccessUnit<ModuleSettingsPO, ModuleSettings>{
	private static final Logger LOG = LoggerFactory.getLogger(ModuleSettingsAccessUnit.class);

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

	public void update(ModuleSettings ms) {
		try {
			retryOnFail(() -> dao.update(ModuleSettingsPO.class, ms));
		} catch(Exception e) {
			LOG.error("Error while trying to update a ModuleSetting");
			LOG.debug("Stacktrace:", e);
		}
	}
}
