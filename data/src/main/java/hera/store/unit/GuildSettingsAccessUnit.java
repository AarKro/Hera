package hera.store.unit;

import hera.database.entity.mapped.GuildSettings;
import hera.database.entity.persistence.GuildSettingsPO;

import java.util.List;
import java.util.stream.Collectors;

public class GuildSettingsAccessUnit extends StorageAccessUnit<GuildSettingsPO, GuildSettings>{

	public GuildSettingsAccessUnit() {
		super(GuildSettingsPO.ENTITY_NAME);
	}

	public List<GuildSettings> forGuild(Long guild) {
		return data.stream().filter((g) -> g.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public List<GuildSettings> forName(String name) {
		return data.stream().filter((g) -> g.getName().equals(name)).collect(Collectors.toList());
	}

	public List<GuildSettings> forGuildAndName(Long guild, String name) {
		return data.stream().filter((g) -> g.getGuild().equals(guild) && g.getName().equals(name)).collect(Collectors.toList());
	}
}
