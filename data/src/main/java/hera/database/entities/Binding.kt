package hera.database.entities

import hera.database.types.BindingType

import javax.persistence.*

@Entity
@Table(name = "binding")
data class Binding(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Column(name = "guildFK")
		var guild: Long? = null,

		@Enumerated(EnumType.STRING)
		@Column(name = "bindingTypeFK")
		var bindingType: BindingType? = null,

		var channelSnowflake: Long? = null
) : PersistenceEntity {
	constructor(guild: Long, bindingType: BindingType, channelSnowflake: Long) : this(null, guild, bindingType, channelSnowflake)

	companion object {
		const val ENTITY_NAME = "Binding"
	}
}
