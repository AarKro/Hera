package hera;

import hera.entity.persistence.*;

public class DBService {

	private DAO<BindingPO> bindings;

	private DAO<BindingTypePO> bindingTypes;

	private DAO<CommandMetricsPO> commandMetrics;

	private DAO<CommandPO> commands;

	private DAO<DefaultRolePO> defaultRoles;

	private DAO<GlobalSettingsPO> globalSettings;

	private DAO<GuildPO> guilds;

	private DAO<GuildSettingsPO> guildSettings;

	private DAO<LocalisationPO> localisations;

	private DAO<ModuleSettingsPO> moduleSettings;

	private DAO<OwnerPO> owners;

	private DAO<RoleMemberPO> roleMembers;

	private DAO<RolePO> roles;

	private DAO<SnowflakeTypePO> snowflakeTypes;

	private DAO<TokenPO> tokens;

	private DAO<UserPO> users;

	public DAO<BindingPO> bindings() {
		if (bindings == null) bindings = new DAO<>(BindingPO.ENTITY_NAME);
		return bindings;
	}

	public DAO<BindingTypePO> bindingTypes() {
		if (bindingTypes == null) bindingTypes = new DAO<>(BindingTypePO.ENTITY_NAME);
		return bindingTypes;
	}

	public DAO<CommandMetricsPO> commandMetrics() {
		if (commandMetrics == null) commandMetrics = new DAO<>(CommandMetricsPO.ENTITY_NAME);
		return commandMetrics;
	}

	public DAO<CommandPO> commands() {
		if (commands == null) commands = new DAO<>(CommandPO.ENTITY_NAME);
		return commands;
	}

	public DAO<DefaultRolePO> defaultRoles() {
		if (defaultRoles == null) defaultRoles = new DAO<>(DefaultRolePO.ENTITY_NAME);
		return defaultRoles;
	}

	public DAO<GlobalSettingsPO> globalSettings() {
		if (globalSettings == null) globalSettings = new DAO<>(GlobalSettingsPO.ENTITY_NAME);
		return globalSettings;
	}

	public DAO<GuildPO> guilds() {
		if (guilds == null) guilds = new DAO<>(GuildPO.ENTITY_NAME);
		return guilds;
	}

	public DAO<GuildSettingsPO> guildSettings() {
		if (guildSettings == null) guildSettings = new DAO<>(GuildSettingsPO.ENTITY_NAME);
		return guildSettings;
	}

	public DAO<LocalisationPO> localisations() {
		if (localisations == null) localisations = new DAO<>(LocalisationPO.ENTITY_NAME);
		return localisations;
	}

	public DAO<ModuleSettingsPO> moduleSettings() {
		if (moduleSettings == null) moduleSettings = new DAO<>(ModuleSettingsPO.ENTITY_NAME);
		return moduleSettings;
	}

	public DAO<OwnerPO> owners() {
		if (owners == null) owners = new DAO<>(OwnerPO.ENTITY_NAME);
		return owners;
	}

	public DAO<RoleMemberPO> roleMembers() {
		if (roleMembers == null) roleMembers = new DAO<>(RoleMemberPO.ENTITY_NAME);
		return roleMembers;
	}

	public DAO<RolePO> roles() {
		if (roles == null) roles = new DAO<>(RolePO.ENTITY_NAME);
		return roles;
	}

	public DAO<SnowflakeTypePO> snowflakeTypes() {
		if (snowflakeTypes == null) snowflakeTypes = new DAO<>(SnowflakeTypePO.ENTITY_NAME);
		return snowflakeTypes;
	}

	public DAO<TokenPO> tokens() {
		if (tokens == null) tokens = new DAO<>(TokenPO.ENTITY_NAME);
		return tokens;
	}

	public DAO<UserPO> users() {
		if (users == null) users = new DAO<>(UserPO.ENTITY_NAME);
		return users;
	}
}
