package hera.database.entities

import hera.database.types.BindingName
import javax.persistence.*

@Entity
@Table(name = "binding_type")
data class BindingType(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Enumerated(EnumType.STRING)
		@Column(name = "type")
		var type: BindingName? = null,

		@ManyToOne
		@JoinColumn(name = "localisationFK")
		var message: Localisation? = null,

		var isGlobal: Boolean? = null
) : IPersistenceEntity {
	constructor(type: BindingName, message: Localisation, isOwner: Boolean) : this(null, type, message, isOwner)
}
