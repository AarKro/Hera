package hera.entity.persistence;

import hera.entity.mapped.Command;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "command")
public class CommandPO {

	public static final String ENTITY_NAME = "CommandPO";

	@Id
	@GeneratedValue
	private int id;

	private String name;

	private String description;

	private int paramCount;

	private boolean infiniteParam;

	private boolean admin;

	public CommandPO() {
	}

	public CommandPO(String name, String description, int paramCount, boolean infiniteParam, boolean admin) {
		this.name = name;
		this.description = description;
		this.paramCount = paramCount;
		this.infiniteParam = infiniteParam;
		this.admin = admin;
	}

	public Command mapToNonePO() {
		return new Command(
				this.id,
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
