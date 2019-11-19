package hera.database.entity.persistence;

import hera.database.entity.mapped.Localisation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "localisation")
public class LocalisationPO implements IPersistenceEntity<Localisation>, Serializable {

	public static final String ENTITY_NAME = "LocalisationPO";

	@Id
	private String language;

	@Id
	private String key;

	private String value;

	public LocalisationPO() {
	}

	public LocalisationPO(String language, String key, String value) {
		this.language = language;
		this.key = key;
		this.value = value;
	}

	public Localisation mapToNonePO() {
		return new Localisation(
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
