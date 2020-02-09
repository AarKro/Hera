package hera.database.entities

import hera.database.types.TokenKey

import javax.persistence.*

@Entity
@Table(name = "token")
data class Token(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		var token: String? = null,

		@Enumerated(EnumType.STRING)
		@Column(name = "name")
		var key: TokenKey? = null,

		var description: String? = null
) : PersistenceEntity {
	constructor(token: String, key: TokenKey, description: String) : this(null, token, key, description)
}
