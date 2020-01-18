package hera.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "module_settings")
public class ModuleSettings implements PersistenceEntity {

	public static final String ENTITY_NAME = "ModuleSettings";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "guildFK")
	private Long guild;

	@ManyToOne
	@JoinColumn(name = "commandFK")
	private Command command;

	private boolean enabled;

	@ManyToOne
	@JoinColumn(name = "roleFK")
	private Role role;

	public ModuleSettings() {
	}

	public ModuleSettings(Long guild, Command command, boolean enabled, Role role) {
		this.guild = guild;
		this.command = command;
		this.enabled = enabled;
		this.role = role;
	}

	public ModuleSettings(int id, Long guild, Command command, boolean enabled, Role role) {
		this.id = id;
		this.guild = guild;
		this.command = command;
		this.enabled = enabled;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
