package hera.store.unit;

import hera.database.entities.ConfigFlag;
import hera.database.entities.ConfigFlagType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class ConfigFlagAccessUnit extends StorageAccessUnit<ConfigFlag> {
	public ConfigFlagAccessUnit() {
		super(ConfigFlag.class);
	}

	public List<ConfigFlag> forGuild(Long guild) {
		return get(Collections.singletonMap("guild", guild));
	}

	public List<ConfigFlag> forGuildAndType(Long guild, ConfigFlagType type) {
		return get(new LinkedHashMap<String, Object>() {{
			put("guild", guild);
			put("configFlagType", type);
		}});
	}
}
