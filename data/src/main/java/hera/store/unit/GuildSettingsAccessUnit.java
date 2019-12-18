package hera.store.unit;

import hera.database.entities.mapped.GuildSettings;
import hera.database.entities.persistence.GuildSettingsPO;
import hera.database.types.GuildSettingKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class GuildSettingsAccessUnit extends StorageAccessUnit<GuildSettingsPO, GuildSettings>{

	public GuildSettingsAccessUnit() {
		super(GuildSettingsPO.ENTITY_NAME);
	}
	private static final Logger LOG = LoggerFactory.getLogger(StorageAccessUnit.class);

	public List<GuildSettings> forGuild(Long guild) {
		return data.stream().filter((g) -> g.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public List<GuildSettings> forKey(GuildSettingKey key) {
		return data.stream().filter((g) -> g.getKey().equals(key)).collect(Collectors.toList());
	}

	public List<GuildSettings> forGuildAndKey(Long guild, GuildSettingKey key) {
		return data.stream().filter((g) -> g.getGuild().equals(guild) && g.getKey().equals(key)).collect(Collectors.toList());
	}

	public void upsert(GuildSettings guildSettings) {
		try {
			List<GuildSettings> foundGuildSettings = this.forGuildAndKey(guildSettings.getGuild(), guildSettings.getKey());

			if (foundGuildSettings.isEmpty()) {
				dao.insert(guildSettings);
				List<GuildSettings> newGuildSettings = this.forGuildAndKey(guildSettings.getGuild(), guildSettings.getKey());

				//TODO problem, entity is added to list without id
				data.add(guildSettings);
			} else {
				foundGuildSettings.get(0).setValue(guildSettings.getValue());
				dao.update(GuildSettingsPO.class, foundGuildSettings.get(0), foundGuildSettings.get(0).getId());
			}
		} catch(Exception e) {
			LOG.error("Error while trying to add entity of type GuildSettingsPO");
			LOG.debug("Stacktrace:", e);
		}
	}
}
