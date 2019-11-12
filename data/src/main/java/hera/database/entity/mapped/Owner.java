package hera.database.entity.mapped;

import hera.database.entity.persistence.OwnerPO;

public class Owner implements IMappedEntity<OwnerPO> {

	public static final String NAME = "Owner";

	private Long user;

	public Owner() {
	}

	public Owner(Long user) {
		this.user = user;
	}

	public OwnerPO mapToPO() {
		return new OwnerPO(
				this.user
		);
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}
}
