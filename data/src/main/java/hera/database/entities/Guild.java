package hera.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "guild")
public class Guild implements PersistenceEntity {

	public static final String ENTITY_NAME = "Guild";

	@Id
	private Long id;

	public Guild() {
	}

	public Guild(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
