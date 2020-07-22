package hera.database.entities

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "guild")
data class Guild(@Id override var id: Long? = null) : IPersistenceEntity
