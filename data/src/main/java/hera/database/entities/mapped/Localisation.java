package hera.database.entities.mapped;

import hera.database.entities.persistence.LocalisationPO;
import hera.database.types.LocalisationKey;

public class Localisation implements IMappedEntity<LocalisationPO>{

	public static final String NAME = "Localisation";

	private String language;

	private LocalisationKey key;

	private String value;

	public Localisation() {
	}

	public Localisation(String language, LocalisationKey key, String value) {
		this.language = language;
		this.key = key;
		this.value = value;
	}

	public LocalisationPO mapToPO() {
		return new LocalisationPO(
				this.language,
				this.key,
				this.value
		);
	}
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public LocalisationKey getKey() {
		return key;
	}

	public void setKey(LocalisationKey key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
