package hera.store.unit;

import hera.database.entities.mapped.GuildSettings;
import hera.database.entities.persistence.GuildSettingsPO;
import hera.database.types.GuildSettingKey;

import java.util.List;
import java.util.stream.Collectors;

public class GuildSettingsAccessUnit extends StorageAccessUnit<GuildSettingsPO, GuildSettings>{

	public GuildSettingsAccessUnit() {
		super(GuildSettingsPO.ENTITY_NAME);
	}

	public List<GuildSettings> forGuild(Long guild) {
		return data.stream().filter((g) -> g.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public List<GuildSettings> forKey(GuildSettingKey key) {
		return data.stream().filter((g) -> g.getKey().equals(key)).collect(Collectors.toList());
	}

	public List<GuildSettings> forGuildAndKey(Long guild, GuildSettingKey key) {
		return data.stream().filter((g) -> g.getGuild().equals(guild) && g.getKey().equals(key)).collect(Collectors.toList());
	}
}
