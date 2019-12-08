package hera.store.unit;

import hera.database.entities.mapped.Localisation;
import hera.database.entities.persistence.LocalisationPO;
import hera.database.types.LocalisationKey;

import java.util.List;
import java.util.stream.Collectors;

public class LocalisationAccessUnit extends StorageAccessUnit<LocalisationPO, Localisation>{

	public LocalisationAccessUnit() {
		super(LocalisationPO.ENTITY_NAME);
	}

	public List<Localisation> forLanguage(String language) {
		return data.stream().filter((l) -> l.getLanguage().equals(language)).collect(Collectors.toList());
	}

	public List<Localisation> forKey(LocalisationKey key) {
		return data.stream().filter((l) -> l.getKey().equals(key)).collect(Collectors.toList());
	}

	public List<Localisation> forLanguageAndKey(String language, LocalisationKey key) {
		return data.stream().filter((l) -> l.getLanguage().equals(language) && l.getKey().equals(key)).collect(Collectors.toList());
	}
}
