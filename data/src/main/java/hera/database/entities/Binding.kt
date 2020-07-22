package hera.database.entities

import javax.persistence.*

@Entity
@Table(name = "binding")
data class Binding(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Column(name = "guildFK")
		var guild: Long? = null,

		@ManyToOne
		@JoinColumn(name = "bindingTypeFK")
		var bindingType: BindingType? = null,

		var snowflake: Long? = null
) : IPersistenceEntity {
	constructor(guild: Long, bindingType: BindingType, channelSnowflake: Long) : this(null, guild, bindingType, channelSnowflake)
}
