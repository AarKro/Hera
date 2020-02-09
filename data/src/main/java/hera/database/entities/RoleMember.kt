package hera.database.entities

import hera.database.types.SnowflakeType

import javax.persistence.*

@Entity
@Table(name = "role_member")
data class RoleMember(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		var snowflake: Long? = null,

		@ManyToOne
		@JoinColumn(name = "roleFK")
		var role: Role? = null,

		@Enumerated(EnumType.STRING)
		@Column(name = "snowflakeTypeFK")
		var snowflakeType: SnowflakeType? = null
) : PersistenceEntity {
	constructor(snowflake: Long, role: Role, snowflakeType: SnowflakeType) : this(null, snowflake, role, snowflakeType)
}
