package hera.database.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User(@Id override var id: Long? = null): PersistenceEntity {
	companion object {
		const val ENTITY_NAME = "User"
	}
}
