package hera.database.entity.mapped;

import hera.database.entity.persistence.IPersistenceEntity;

public interface IMappedEntity<T extends IPersistenceEntity> {

	T mapToPO();
}
