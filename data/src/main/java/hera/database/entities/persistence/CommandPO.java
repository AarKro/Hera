package hera.database.entities.persistence;

import hera.database.entities.mapped.Command;
import hera.database.types.CommandName;

import javax.persistence.*;

@Entity
@Table(name = "command")
public class CommandPO implements IPersistenceEntity<Command> {

	public static final String ENTITY_NAME = "CommandPO";

	@Id
	private int id;

	@Enumerated(EnumType.STRING)
	private CommandName name;

	private String description;

	private int paramCount;

	private boolean infiniteParam;

	private int level;

	public CommandPO() {
	}

	public CommandPO(CommandName name, String description, int paramCount, boolean infiniteParam, int level) {
		this.name = name;
		this.description = description;
		this.paramCount = paramCount;
		this.infiniteParam = infiniteParam;
		this.level = level;
	}

	public Command mapToNonePO() {
		return new Command(
				this.id,
				this.name,
				this.description,
				this.paramCount,
				this.infiniteParam,
				this.level
		);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CommandName getName() {
		return name;
	}

	public void setName(CommandName name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getParamCount() {
		return paramCount;
	}

	public void setParamCount(int paramCount) {
		this.paramCount = paramCount;
	}

	public boolean isInfiniteParam() {
		return infiniteParam;
	}

	public void setInfiniteParam(boolean infiniteParam) {
		this.infiniteParam = infiniteParam;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
