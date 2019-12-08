package hera.store.unit;

import hera.database.entities.mapped.Command;
import hera.database.entities.persistence.CommandPO;
import hera.database.types.CommandName;

import java.util.List;
import java.util.stream.Collectors;

public class CommandAccessUnit extends StorageAccessUnit<CommandPO, Command> {

	public CommandAccessUnit() {
		super(CommandPO.ENTITY_NAME);
	}

	public List<Command> forName(CommandName name) {
		return data.stream().filter((c) -> c.getName().equals(name)).collect(Collectors.toList());
	}

	public List<Command> forName(String name) {
		return data.stream().filter((c) -> c.getName().name().equals(name.toUpperCase())).collect(Collectors.toList());
	}
}
