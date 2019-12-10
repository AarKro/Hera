package hera.database.entities.persistence;

import hera.database.entities.mapped.Role;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RolePO implements IPersistenceEntity<Role>{

	public static final String ENTITY_NAME = "RolePO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Long guildFK;

	private int parent;

	private String name;

	private String description;

	public RolePO() {
	}

	public RolePO(Long guildFK, String name, String description) {
		this.guildFK = guildFK;
		this.name = name;
		this.description = description;
	}

	public RolePO(Long guildFK, int parent, String name, String description) {
		this.guildFK = guildFK;
		this.parent = parent;
		this.name = name;
		this.description = description;
	}

	public Role mapToNonePO() {
		return new Role(
				this.id,
				this.guildFK,
				this.parent,
				this.name,
				this.description
		);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getGuildFK() {
		return guildFK;
	}

	public void setGuildFK(Long guildFK) {
		this.guildFK = guildFK;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
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
