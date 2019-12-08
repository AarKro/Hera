package hera.database.entities.mapped;

import hera.database.entities.persistence.IPersistenceEntity;

public interface IMappedEntity<T extends IPersistenceEntity> {

	T mapToPO();
}
