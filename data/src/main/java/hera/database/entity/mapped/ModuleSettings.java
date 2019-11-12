package hera.database.entity.mapped;

import hera.database.entity.persistence.ModuleSettingsPO;

public class ModuleSettings implements IMappedEntity<ModuleSettingsPO> {

	public static final String NAME = "ModuleSettings";

	private Long guild;

	private int command;

	private boolean enabled;

	private int role;

	public ModuleSettings() {
	}

	public ModuleSettings(Long guild, int command, boolean enabled, int role) {
		this.guild = guild;
		this.command = command;
		this.enabled = enabled;
		this.role = role;
	}

	public ModuleSettingsPO mapToPO() {
		return new ModuleSettingsPO(
				this.guild,
				this.command,
				this.enabled,
				this.role
		);
	}

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
