package hera.store.unit;

import hera.database.entities.Command;
import hera.database.types.CommandName;

import java.util.Collections;
import java.util.List;

public class CommandAccessUnit extends StorageAccessUnit<Command> {

	public CommandAccessUnit() {
		super(Command.class, Command.ENTITY_NAME);
	}

	public List<Command> forName(CommandName name) {
		return get(Collections.singletonMap("name", name.name()));
	}

	public List<Command> forName(String name) {
		return get(Collections.singletonMap("name", name));
	}
}
