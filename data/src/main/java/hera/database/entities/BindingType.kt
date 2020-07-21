package hera.database.entities

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

		var isGlobal: Boolean? = null
) : IPersistenceEntity {
	constructor(type: String, message: Localisation, isOwner: Boolean) : this(null, type, message, isOwner)
}
