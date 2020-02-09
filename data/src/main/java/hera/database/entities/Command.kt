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

		var description: String? = null,

		var paramCount: Int? = null,

		var optionalParams: Int? = null,

		var level: Int? = null
) : PersistenceEntity {
	constructor(name: CommandName, description: String, paramCount: Int, optionalParams: Int, level: Int) : this(null, name, description, paramCount, optionalParams, level)
}
