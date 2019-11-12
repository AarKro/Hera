package hera.database.entity.mapped;

import hera.database.entity.persistence.LocalisationPO;

public class Localisation implements IMappedEntity<LocalisationPO>{

	public static final String NAME = "Localisation";

	private String language;

	private String key;

	private String value;

	public Localisation() {
	}

	public Localisation(String language, String key, String value) {
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
