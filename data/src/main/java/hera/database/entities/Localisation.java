package hera.database.entities;

import hera.database.types.LocalisationKey;

import javax.persistence.*;

@Entity
@Table(name = "localisation")
public class Localisation implements PersistenceEntity {

	public static final String ENTITY_NAME = "Localisation";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String language;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private LocalisationKey key;

	private String value;

	public Localisation() {
	}

	public Localisation(String language, LocalisationKey key, String value) {
		this.language = language;
		this.key = key;
		this.value = value;
	}

	public Localisation(int id, String language, LocalisationKey key, String value) {
		this.id = id;
		this.language = language;
		this.key = key;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
