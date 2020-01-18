package hera.store.unit;

import hera.database.entities.Localisation;
import hera.database.types.LocalisationKey;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class LocalisationAccessUnit extends StorageAccessUnit<Localisation>{

	public LocalisationAccessUnit() {
		super(Localisation.class, Localisation.ENTITY_NAME);
	}

	public List<Localisation> forLanguage(String language) {
		return get(Collections.singletonMap("language", language));
	}

	public List<Localisation> forKey(LocalisationKey key) {
		return get(Collections.singletonMap("key", key.name()));
	}

	public List<Localisation> forLanguageAndKey(String language, LocalisationKey key) {
		return get(new LinkedHashMap<String, Object>() {{
			put("language", language);
			put("key", key.name());
		}});
	}
}
