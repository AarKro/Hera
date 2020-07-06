package hera.database.entities

import hera.database.types.SnowflakeType

import javax.persistence.*

@Entity
@Table(name = "binding_type_joined")
data class BindingType(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Column(name = "type")
		var type: String? = null,

		@ManyToOne
		@JoinColumn(name = "localisationFK")
		var message: Localisation? = null,

		@Enumerated(EnumType.STRING)
		@Column(name = "snowflakeType")
		var snowflakeType: SnowflakeType? = null
) : PersistenceEntity {
	constructor(type: String, message: Localisation, snowflakeType: SnowflakeType) : this(null, type, message, snowflakeType)
}
