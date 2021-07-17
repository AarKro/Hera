package hera.database.entities

import hera.database.types.ConfigFlagName
import javax.persistence.*

@Entity
@Table(name = "config_flag_type")
data class ConfigFlagType(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Enumerated(EnumType.STRING)
		var name: ConfigFlagName? = null,

		var isDefault: Boolean? = null,

		var level: Int? = null
) : IPersistenceEntity {
	constructor(name: ConfigFlagName?, isDefault: Boolean?, level: Int?) : this(null, name, isDefault, level)
}
