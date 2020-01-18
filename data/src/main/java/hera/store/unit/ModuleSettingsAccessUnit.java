package hera.store.unit;

import hera.database.entities.ModuleSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class ModuleSettingsAccessUnit extends StorageAccessUnit<ModuleSettings>{
	private static final Logger LOG = LoggerFactory.getLogger(ModuleSettingsAccessUnit.class);

	public ModuleSettingsAccessUnit() {
		super(ModuleSettings.class, ModuleSettings.ENTITY_NAME);
	}

	public List<ModuleSettings> forGuild(Long guild) {
		return get(Collections.singletonMap("guild", guild));
	}

	public List<ModuleSettings> forCommand(int command) {
		return get(Collections.singletonMap("command", command));
	}

	public List<ModuleSettings> forModule(Long guild, int command) {
		return get(new LinkedHashMap<String, Object>() {{
			put("guild", guild);
			put("command", command);
		}});
	}
}
