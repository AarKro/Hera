package hera.store.unit;

import hera.database.entities.Command;
import hera.database.types.CommandName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandAccessUnit extends StorageAccessUnit<Command> {

	public CommandAccessUnit() {
		super(Command.class);
	}

	public List<Command> forName(CommandName name) {
		return get(Collections.singletonMap("name", name));
	}

	public List<Command> forName(String name) {
		List<CommandName> cm = Arrays.stream(CommandName.values())
				.filter(c -> c.name().equals(name.toUpperCase()))
				.collect(Collectors.toList());

		if (cm.isEmpty()) return Collections.emptyList();

		return get(Collections.singletonMap("name", cm));
	}
}
