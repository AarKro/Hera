package hera.store.unit;

import hera.database.entities.mapped.Binding;
import hera.database.entities.persistence.BindingPO;
import hera.database.types.BindingType;

import java.util.List;
import java.util.stream.Collectors;

public class BindingAccessUnit extends StorageAccessUnit<BindingPO, Binding>{

	public BindingAccessUnit() {
		super(BindingPO.ENTITY_NAME);
	}

	public List<Binding> forGuild(Long guild) {
		return data.stream().filter((b) -> b.getGuild().equals(guild)).collect(Collectors.toList());
	}

	public List<Binding> forGuildAndType(Long guild, BindingType type) {
		return data.stream().filter((b) -> b.getGuild().equals(guild) && b.getBindingType() == type.getValue()).collect(Collectors.toList());
	}
}
