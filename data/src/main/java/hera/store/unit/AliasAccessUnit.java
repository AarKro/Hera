package hera.store.unit;

import hera.database.entities.Alias;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class AliasAccessUnit extends StorageAccessUnit<Alias>{

	public AliasAccessUnit() {
		super(Alias.class);
	}

	public List<Alias> forGuild(Long guild) {
		return get(Collections.singletonMap("guild", guild));
	}

	public List<Alias> forGuildAndAlias(Long guild, String alias) {
		List<Alias> globalAliases = get(new LinkedHashMap<String, Object>() {{
			put("guild", null);
			put("alias", alias.toUpperCase());
		}});

		List<Alias> guildAliases = get(new LinkedHashMap<String, Object>() {{
			put("guild", guild);
			put("alias", alias.toUpperCase());
		}});

		List<Alias> aliases = new ArrayList<>();
		aliases.addAll(globalAliases);
		aliases.addAll(guildAliases);

		return aliases;
	}

	public boolean exists(String alias, Long guild) {
		return forGuildAndAlias(guild, alias).size() > 0;
	}
}
