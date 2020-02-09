package hera.database.entities

import javax.persistence.*

@Entity
@Table(name = "role")
data class Role(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Column(name = "guildFK")
		var guild: Long? = null,

		@ManyToOne
		@JoinColumn(name = "parent")
		var parent: Role? = null,

		var name: String? = null,

		var description: String? = null
) : PersistenceEntity {
	constructor(guild: Long, parent: Role?, name: String, description: String) : this(null, guild, parent, name, description)
}
