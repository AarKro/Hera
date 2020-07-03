package hera.database.entities

import hera.database.types.LocalisationKey

import javax.persistence.*

@Entity
@Table(name = "localisation")
data class Localisation(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		var language: String? = null,

		@Enumerated(EnumType.STRING)
		@Column(name = "name")
		var key: LocalisationKey? = null,

		var value: String? = null
) : IPersistenceEntity {
	constructor(language: String, key: LocalisationKey, value: String) : this(null, language, key, value)
}
