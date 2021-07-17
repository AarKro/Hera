package hera.core.util;

import discord4j.core.object.entity.Guild;
import hera.database.entities.ConfigFlag;
import hera.database.entities.ConfigFlagType;
import hera.database.types.ConfigFlagName;
import sun.util.resources.cldr.ext.LocaleNames_zgh;

import java.util.List;

import static hera.store.DataStore.STORE;

public class ConfigUtil {



	public static ConfigFlag getConfigForGuild(ConfigFlagName name, Guild guild) {

		List<ConfigFlagType> type = STORE.configFlagTypes().forName(name);
		if (type.size() != 1) {
			throw new RuntimeException("Error getting entity of type ConfigFlagType");
		}
		List<ConfigFlag> flags = STORE.configFlags().forGuildAndType(guild.getId().asLong(), type.get(0));
		if (flags.size() != 1) {
			throw new RuntimeException("Error getting entity of type ConfigFlag");
		}
		return flags.get(0);
	}
}
