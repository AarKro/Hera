package hera.database.entities

import hera.database.types.MetricKey

import javax.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "metric")
data class Metric(
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		override var id: Long? = null,

		@Enumerated(EnumType.STRING)
		@Column(name = "name")
		var key: MetricKey? = null,

		var date: Timestamp? = null,

		@ManyToOne
		@JoinColumn(name = "commandFK")
		var command: Command? = null,

		@Column(name = "guildFK")
		var guild: Long? = null,

		@Column(name = "userFK")
		var user: Long? = null,

		var value: Long? = null,

		var details: String? = null
) : PersistenceEntity {
	constructor(key: MetricKey, date: Timestamp, command: Command?, guild: Long, user: Long, value: Long?, details: String?) : this(null, key, date, command, guild, user, value, details)
}
