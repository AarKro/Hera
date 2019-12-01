package hera.database.entities.persistence;

import hera.database.entities.mapped.IMappedEntity;

public interface IPersistenceEntity<T extends IMappedEntity> {

	T mapToNonePO();
}
