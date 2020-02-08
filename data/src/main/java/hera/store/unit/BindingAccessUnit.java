package hera.store.unit;

import hera.database.entities.Binding;
import hera.database.types.BindingType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class BindingAccessUnit extends StorageAccessUnit<Binding>{

	public BindingAccessUnit() {
		super(Binding.class);
	}

	public List<Binding> forGuild(Long guild) {
		return get(Collections.singletonMap("guild", guild));
	}

	public List<Binding> forGuildAndType(Long guild, BindingType type) {
		return get(new LinkedHashMap<String, Object>() {{
			put("guild", guild);
			put("bindingType", type);
		}});
	}
}
