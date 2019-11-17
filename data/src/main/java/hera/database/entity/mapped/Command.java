package hera.database.entity.mapped;

import hera.database.entity.persistence.CommandPO;

public class Command implements IMappedEntity<CommandPO> {

	public static final String NAME = "Command";

	private int id;

	private String name;

	private String description;

	private int paramCount;

	private boolean infiniteParam;

	private boolean admin;

	public Command() {
	}

	public Command(String name, String description, int paramCount, boolean infiniteParam, boolean admin) {
		this.name = name;
		this.description = description;
		this.paramCount = paramCount;
		this.infiniteParam = infiniteParam;
		this.admin = admin;
	}

	public Command(int id, String name, String description, int paramCount, boolean infiniteParam, boolean admin) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.paramCount = paramCount;
		this.infiniteParam = infiniteParam;
		this.admin = admin;
	}

	public CommandPO mapToPO() {
		return new CommandPO(
				this.name,
				this.description,
				this.paramCount,
				this.infiniteParam,
				this.admin
		);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
