package hera.store.unit;

import hera.database.entities.GuildSetting;
import hera.database.types.GuildSettingKey;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class GuildSettingAccessUnit extends StorageAccessUnit<GuildSetting>{

	public GuildSettingAccessUnit() {
		super(GuildSetting.class);
	}

	public List<GuildSetting> forGuild(Long guild) {
		return get(Collections.singletonMap("guild", guild));
	}

	public List<GuildSetting> forKey(GuildSettingKey key) {
		return get(Collections.singletonMap("key", key));
	}

	public List<GuildSetting> forGuildAndKey(Long guild, GuildSettingKey key) {
		return get(new LinkedHashMap<String, Object>() {{
			put("guild", guild);
			put("key", key);
		}});
	}
}
