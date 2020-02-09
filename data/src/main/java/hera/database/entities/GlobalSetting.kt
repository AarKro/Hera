package hera.database.entities

import hera.database.types.GlobalSettingKey

import javax.persistence.*

@Entity
@Table(name = "global_setting")
data class GlobalSetting(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Enumerated(EnumType.STRING)
		@Column(name = "name")
		var key: GlobalSettingKey? = null,

		var value: String? = null
) : PersistenceEntity {
	constructor(key: GlobalSettingKey, value: String) : this(null, key, value)
}
