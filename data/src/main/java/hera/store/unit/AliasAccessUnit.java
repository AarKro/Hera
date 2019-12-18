package hera.store.unit;

import hera.database.entities.mapped.Alias;
import hera.database.entities.persistence.AliasPO;

import java.util.List;
import java.util.stream.Collectors;

public class AliasAccessUnit extends StorageAccessUnit<AliasPO, Alias>{

	public AliasAccessUnit() {
		super(AliasPO.ENTITY_NAME);
	}

	public List<Object> forGuild(Long guild) {
		return data.stream().filter((b) -> b.getGuild() == null || b.getGuild().equals(guild)).collect(Collectors.toList());
	}
}
