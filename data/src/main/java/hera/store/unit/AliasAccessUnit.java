package hera.store.unit;

import hera.database.entities.Alias;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class AliasAccessUnit extends StorageAccessUnit<Alias>{

	public AliasAccessUnit() {
		super(Alias.class, Alias.ENTITY_NAME);
	}

	public List<Alias> forGuild(Long guild) {
		return get(Collections.singletonMap("guild", guild));
	}

	public List<Alias> forGuildAndAlias(Long guild, String alias) {
		return get(new LinkedHashMap<String, Object>() {{
			put("guild", guild);
			put("alias", alias.toUpperCase());
		}});
	}

	public boolean exists(String alias, Long guild) {
		return forGuildAndAlias(guild, alias).size() > 0;
	}
}
