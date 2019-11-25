package hera.store.unit;

import hera.database.entity.mapped.Binding;
import hera.database.entity.persistence.BindingPO;

import java.util.List;
import java.util.stream.Collectors;

public class BindingAccessUnit extends StorageAccessUnit<BindingPO, Binding>{

	public BindingAccessUnit() {
		super(BindingPO.ENTITY_NAME);
	}

	public List<Binding> forGuild(Long guild) {
		return data.stream().filter((b) -> b.getGuild().equals(guild)).collect(Collectors.toList());
	}
}
