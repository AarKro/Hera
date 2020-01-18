package hera.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "default_role")
public class DefaultRole implements PersistenceEntity {

	public static final String ENTITY_NAME = "DefaultRole";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "guildFK")
	private Long guild;

	@ManyToOne
	@JoinColumn(name = "roleFK")
	private Role role;

	public DefaultRole() {
	}

	public DefaultRole(Long guild, Role role) {
		this.guild = guild;
		this.role = role;
	}

	public DefaultRole(int id, Long guild, Role role) {
		this.id = id;
		this.guild = guild;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
