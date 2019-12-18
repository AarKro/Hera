package hera.store.unit;

import hera.database.entities.mapped.Alias;
import hera.database.entities.persistence.AliasPO;

import java.util.List;
import java.util.stream.Collectors;

public class AliasAccessUnit extends StorageAccessUnit<AliasPO, Alias>{

	public AliasAccessUnit() {
		super(AliasPO.ENTITY_NAME);
	}

	public List<Alias> forGuildAndAlias(Long guild, String alias) {
		return data.stream().filter((a) -> a.getGuild() == null || a.getGuild().equals(guild))
				.filter((a) -> a.getAlias().equalsIgnoreCase(alias)).collect(Collectors.toList());
	}

	public List<Alias> forGuild(Long guild) {
		return data.stream().filter((a) -> a.getGuild() == null || a.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public boolean exists(String alias, Long guild) {
		return data.stream().filter((a) -> a.getGuild() == null || a.getGuild().equals(guild))
				.filter((a) -> a.getAlias().equalsIgnoreCase(alias))
				.collect(Collectors.toList()).size() > 0;
	}
}
