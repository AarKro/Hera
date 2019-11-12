package hera.database.entity.persistence;

import hera.database.entity.mapped.IMappedEntity;

public interface IPersistenceEntity<T extends IMappedEntity> {

	T mapToNonePO();
}
