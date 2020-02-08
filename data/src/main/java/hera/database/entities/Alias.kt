package hera.database.entities

import javax.persistence.*

@Entity
@Table(name = "alias")
data class Alias(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@ManyToOne
		@JoinColumn(name = "commandFK")
		var command: Command? = null,

		var alias: String? = null,

		@Column(name = "guildFK")
		var guild: Long? = null
) : PersistenceEntity {
	constructor(command: Command, alias: String, guild: Long?) : this(null, command, alias, guild)

	companion object {
		const val ENTITY_NAME = "Alias"
	}
}
