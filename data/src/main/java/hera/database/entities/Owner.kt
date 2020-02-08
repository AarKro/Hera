package hera.database.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "owner")
data class Owner(@Id override var id: Long? = null) : PersistenceEntity {
	companion object {
		const val ENTITY_NAME = "Owner"
	}
}
