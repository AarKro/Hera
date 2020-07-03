package hera.database.entities

import hera.database.types.GuildSettingKey

import javax.persistence.*

@Entity
@Table(name = "guild_setting")
data class GuildSetting(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Column(name = "guildFK")
		var guild: Long? = null,

		@Enumerated(EnumType.STRING)
		@Column(name = "name")
		var key: GuildSettingKey? = null,

		var value: String? = null
) : IPersistenceEntity {
	constructor(guild: Long, key: GuildSettingKey, value: String) : this(null, guild, key, value)
}
