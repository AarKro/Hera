package hera.database.entity.mapped;

public class Owner implements IMappedEntity {

	public static final String NAME = "Owner";

	private Long user;

	public Owner() {
	}

	public Owner(Long user) {
		this.user = user;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}
}
