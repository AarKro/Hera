package hera.store.unit;

import hera.database.entities.mapped.GuildSetting;
import hera.database.entities.persistence.GuildSettingPO;
import hera.database.types.GuildSettingKey;
import hera.store.exception.FailedAfterRetriesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class GuildSettingAccessUnit extends StorageAccessUnit<GuildSettingPO, GuildSetting>{

	public GuildSettingAccessUnit() {
		super(GuildSettingPO.ENTITY_NAME);
	}
	private static final Logger LOG = LoggerFactory.getLogger(GuildSettingAccessUnit.class);

	public List<GuildSetting> forGuild(Long guild) {
		return data.stream().filter((g) -> g.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public List<GuildSetting> forKey(GuildSettingKey key) {
		return data.stream().filter((g) -> g.getKey().equals(key)).collect(Collectors.toList());
	}

	public List<GuildSetting> forGuildAndKey(Long guild, GuildSettingKey key) {
		return data.stream().filter((g) -> g.getGuild().equals(guild) && g.getKey().equals(key)).collect(Collectors.toList());
	}

	public void upsert(GuildSetting guildSetting) {
		try {
			List<GuildSetting> foundGuildSettings = forGuildAndKey(guildSetting.getGuild(), guildSetting.getKey());

			if (foundGuildSettings.isEmpty()) {
				retryOnFail(() -> {
					GuildSetting gs = dao.insert(guildSetting);
					data.add(gs);
				});
			} else {
				foundGuildSettings.get(0).setValue(guildSetting.getValue());
				retryOnFail(() -> dao.update(GuildSettingPO.class, foundGuildSettings.get(0), foundGuildSettings.get(0).getId()));
			}
		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to add entity of type GuildSettingsPO");
			LOG.debug("Stacktrace:", e);
		}
	}
}
