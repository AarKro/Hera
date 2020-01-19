package hera.database.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
public class Owner implements PersistenceEntity {

	public static final String ENTITY_NAME = "Owner";

	@Id
	private Long id;

	public Owner() {
	}

	public Owner(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
