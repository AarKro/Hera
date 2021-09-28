package hera.core.util;

import discord4j.core.object.entity.Guild;
import hera.database.entities.GuildSetting;
import hera.database.entities.Localisation;
import hera.database.types.GuildSettingKey;
import hera.database.types.LocalisationKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static hera.store.DataStore.STORE;

public class LocalisationUtil {
	public static final Logger LOG = LoggerFactory.getLogger(LocalisationUtil.class);

	//TODO make this actual localizations
	public static final Localisation LOCALISATION_GENERAL_ERROR = new Localisation("en", LocalisationKey.ERROR, "Seems like something went wrong... please try again");
	public static final Localisation LOCALISATION_PERMISSION_ERROR = new Localisation("en", LocalisationKey.ERROR, "You do not have the necessary permissions to use this command");
	public static final Localisation LOCALISATION_PARAM_ERROR = new Localisation("en", LocalisationKey.ERROR, "Command was not used correctly");

	private static final String DEFAULT_LANGUAGE = "en";

	public static Localisation getLocalisation(LocalisationKey key, Guild guild) {
		return getLocalisation(key, guild, LOCALISATION_GENERAL_ERROR);
	}

	public static Localisation getLocalisation(LocalisationKey key, Guild guild, Localisation defaultLocalization) {
		List<GuildSetting> settings = STORE.guildSettings().forGuildAndKey(guild.getId().asLong(), GuildSettingKey.LANGUAGE);

		String language;
		if (settings.isEmpty()) language = DEFAULT_LANGUAGE;
		else language = settings.get(0).getValue();

		List<Localisation> messages;
		messages = STORE.localisations().forLanguageAndKey(language, key);

		if (messages.isEmpty() && !language.equals(DEFAULT_LANGUAGE)) {
			LOG.debug("message for custom language '{}' not found, get standard english localisation instead", language);
			messages = STORE.localisations().forLanguageAndKey(DEFAULT_LANGUAGE, key);
			if (messages.isEmpty()) {
				LOG.error("No localisation found for {} {}", language, key.name());
				return defaultLocalization;
			}

			return messages.get(0);
		} else if (messages.isEmpty()) {
			LOG.error("No localisation found for {} {}", language, key.name());
			return defaultLocalization;
		} else {
			return messages.get(0);
		}
	}
}
