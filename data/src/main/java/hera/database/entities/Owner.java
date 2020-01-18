package hera.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "owner")
public class Owner implements PersistenceEntity {

	public static final String ENTITY_NAME = "Owner";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "userFK")
	private Long user;

	public Owner() {
	}

	public Owner(Long user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}
}
