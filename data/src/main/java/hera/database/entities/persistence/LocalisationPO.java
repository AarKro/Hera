package hera.database.entities.persistence;

import hera.database.entities.mapped.Localisation;
import hera.database.types.LocalisationKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "localisation")
public class LocalisationPO implements IPersistenceEntity<Localisation>, Serializable {

	public static final String ENTITY_NAME = "LocalisationPO";

	@Id
	private String language;

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private LocalisationKey key;

	private String value;

	public LocalisationPO() {
	}

	public LocalisationPO(String language, LocalisationKey key, String value) {
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
