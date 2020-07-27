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
		var name: ConfigFlagName? = null
) : IPersistenceEntity {
	constructor(name: ConfigFlagName?) : this(null, name)
}
