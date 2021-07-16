package hera.core.util;

import discord4j.core.object.entity.Guild;
import hera.database.entities.Command;
import hera.database.entities.ModuleSettings;

import java.util.List;

import static hera.store.DataStore.STORE;

public class CommandUtil {
	public static Boolean isCommandEnabled(Command command, Guild guild) {
		List<ModuleSettings> msList = STORE.moduleSettings().forModule(guild.getId().asLong(), command);
		ModuleSettings ms = !msList.isEmpty() ? msList.get(0) : null;
		return ms == null || ms.isEnabled();
	}
}
