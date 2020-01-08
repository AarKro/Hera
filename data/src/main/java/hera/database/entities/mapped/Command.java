package hera.database.entities.mapped;

import hera.database.entities.persistence.CommandPO;
import hera.database.types.CommandName;

public class Command implements IMappedEntity<CommandPO> {

	public static final String NAME = "Command";

	private int id;

	private CommandName name;

	private String description;

	private int paramCount;

	private boolean infiniteParam;

	private int level;

	public Command() {
	}

	public Command(CommandName name, String description, int paramCount, boolean infiniteParam, int level) {
		this.name = name;
		this.description = description;
		this.paramCount = paramCount;
		this.infiniteParam = infiniteParam;
		this.level = level;
	}

	public Command(int id, CommandName name, String description, int paramCount, boolean infiniteParam, int level) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.paramCount = paramCount;
		this.infiniteParam = infiniteParam;
		this.level = level;
	}

	public CommandPO mapToPO() {
		return new CommandPO(
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
