package hera.database.entities

import javax.persistence.*

@Entity
@Table(name = "config_flag")
data class ConfigFlag(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Column(name = "guildFK")
		var guild: Long? = null,

		@ManyToOne
		@JoinColumn(name = "configFlagTypeFK")
		var configFlagType: ConfigFlagType? = null,

		var value: Boolean? = null
) : IPersistenceEntity {
	constructor(guild: Long?, configFlagType: ConfigFlagType?, value: Boolean?) : this(null, guild, configFlagType, value)
}
