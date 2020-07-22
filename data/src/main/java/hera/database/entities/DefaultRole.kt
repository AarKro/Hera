package hera.database.entities

import javax.persistence.*

@Entity
@Table(name = "default_role")
data class DefaultRole(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Column(name = "guildFK")
		var guild: Long? = null,

		@ManyToOne
		@JoinColumn(name = "roleFK")
		var role: Role? = null
) : IPersistenceEntity {
	constructor(guild: Long, role: Role) : this(null, guild, role)
}
