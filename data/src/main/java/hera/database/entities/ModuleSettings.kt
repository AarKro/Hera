package hera.database.entities

import javax.persistence.*

@Entity
@Table(name = "module_settings")
data class ModuleSettings(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Column(name = "guildFK")
		var guild: Long? = null,

		@ManyToOne
		@JoinColumn(name = "commandFK")
		var command: Command? = null,

		@Column(name = "enabled")
		var isEnabled: Boolean? = null,

		@ManyToOne
		@JoinColumn(name = "roleFK")
		var role: Role? = null
) : IPersistenceEntity {
	constructor(guild: Long, command: Command, enabled: Boolean, role: Role?) : this(null, guild, command, enabled, role)
}
