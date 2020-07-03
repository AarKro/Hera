package hera.database.entities

import hera.database.types.CommandName

import javax.persistence.*

@Entity
@Table(name = "command")
data class Command(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Enumerated(EnumType.STRING)
		var name: CommandName? = null,

		@ManyToOne
		@JoinColumn(name = "description")
		var description: Localisation? = null,

		var paramCount: Int? = null,

		var optionalParams: Int? = null,

		var level: Int? = null,

		var minPermission: Int? = null
) : IPersistenceEntity {
	constructor(name: CommandName, description: Localisation, paramCount: Int, optionalParams: Int, level: Int) : this(null, name, description, paramCount, optionalParams, level)
}
