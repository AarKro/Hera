package hera.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User implements PersistenceEntity {

	public static final String ENTITY_NAME = "User";

	@Id
	private Long id;

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
