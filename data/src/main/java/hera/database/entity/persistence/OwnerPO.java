package hera.database.entity.persistence;

import hera.database.entity.mapped.Owner;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
public class OwnerPO implements IPersistenceEntity<Owner>{

	public static final String ENTITY_NAME = "OwnerPO";

	@Id
	private Long userFK;

	public OwnerPO() {
	}

	public OwnerPO(Long userFK) {
		this.userFK = userFK;
	}

	public Owner mapToNonePO() {
		return new Owner(
				this.userFK
		);
	}

	public Long getUserFK() {
		return userFK;
	}

	public void setUserFK(Long userFK) {
		this.userFK = userFK;
	}
}
