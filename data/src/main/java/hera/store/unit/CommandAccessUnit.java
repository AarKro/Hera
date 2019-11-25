package hera.store.unit;

import hera.database.entity.mapped.Command;
import hera.database.entity.persistence.CommandPO;

import java.util.List;
import java.util.stream.Collectors;

public class CommandAccessUnit extends StorageAccessUnit<CommandPO, Command> {

	public CommandAccessUnit() {
		super(CommandPO.ENTITY_NAME);
	}

	public List<Command> forName(String name) {
		return data.stream().filter((c) -> c.getName().equals(name)).collect(Collectors.toList());
	}
}
