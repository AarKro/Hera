package hera.database.entities.persistence;

import hera.database.entities.mapped.ModuleSettings;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "module_settings")
public class ModuleSettingsPO implements IPersistenceEntity<ModuleSettings>, Serializable {

	public static final String ENTITY_NAME = "ModuleSettingsPO";

	@Id
	private Long guildFK;

	@Id
	private int commandFK;

	private boolean enabled;

	private Integer roleFK;

	public ModuleSettingsPO() {
	}

	public ModuleSettingsPO(Long guildFK, int commandFK, boolean enabled) {
		this.guildFK = guildFK;
		this.commandFK = commandFK;
		this.enabled = enabled;
	}

	public ModuleSettingsPO(Long guildFK, int commandFK, boolean enabled, Integer roleFK) {
		this.guildFK = guildFK;
		this.commandFK = commandFK;
		this.enabled = enabled;
		this.roleFK = roleFK;
	}

	public ModuleSettings mapToNonePO() {
		return new ModuleSettings(
				this.guildFK,
				this.commandFK,
				this.enabled,
				this.roleFK
		);
	}

	public Long getGuildFK() {
		return guildFK;
	}

	public void setGuildFK(Long guildFK) {
		this.guildFK = guildFK;
	}

	public int getCommandFK() {
		return commandFK;
	}

	public void setCommandFK(int commandFK) {
		this.commandFK = commandFK;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getRoleFK() {
		return roleFK;
	}

	public void setRoleFK(Integer roleFK) {
		this.roleFK = roleFK;
	}
}
