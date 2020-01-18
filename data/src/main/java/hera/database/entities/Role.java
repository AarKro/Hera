package hera.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role implements PersistenceEntity {

	public static final String ENTITY_NAME = "Role";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "guildFK")
	private Long guild;

	@ManyToOne
	@JoinColumn(name = "parent")
	private Role parent;

	private String name;

	private String description;

	public Role() {
	}

	public Role(Long guild, Role parent, String name, String description) {
		this.guild = guild;
		this.parent = parent;
		this.name = name;
		this.description = description;
	}

	public Role(int id, Long guild, Role parent, String name, String description) {
		this.id = id;
		this.guild = guild;
		this.parent = parent;
		this.name = name;
		this.description = description;
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

	public Role getParent() {
		return parent;
	}

	public void setParent(Role parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
