package hera.store;

import hera.database.entity.mapped.*;
import hera.database.entity.persistence.*;

public class DataStore {

	private static DataStoreUnit<BindingPO, Binding> bindings;

	private static DataStoreUnit<BindingTypePO, BindingType> bindingTypes;

	private static DataStoreUnit<CommandMetricsPO, CommandMetrics> commandMetrics;

	private static DataStoreUnit<CommandPO, Command> commands;

	private static DataStoreUnit<DefaultRolePO, DefaultRole> defaultRoles;

	private static DataStoreUnit<GlobalSettingsPO, GlobalSettings> globalSettings;

	private static DataStoreUnit<GuildPO, Guild> guilds;

	private static DataStoreUnit<GuildSettingsPO, GuildSettings> guildSettings;

	private static DataStoreUnit<LocalisationPO, Localisation> localisations;

	private static DataStoreUnit<ModuleSettingsPO, ModuleSettings> moduleSettings;

	private static DataStoreUnit<OwnerPO, Owner> owners;

	private static DataStoreUnit<RoleMemberPO, RoleMember> roleMembers;

	private static DataStoreUnit<RolePO, Role> roles;

	private static DataStoreUnit<SnowflakeTypePO, SnowflakeType> snowflakeTypes;

	private static DataStoreUnit<TokenPO, Token> tokens;

	private static DataStoreUnit<UserPO, User> users;

	public static void initialize() {
		bindings = new DataStoreUnit<>(BindingPO.ENTITY_NAME);
		bindingTypes = new DataStoreUnit<>(BindingTypePO.ENTITY_NAME);
		commandMetrics = new DataStoreUnit<>(CommandMetricsPO.ENTITY_NAME);
		commands = new DataStoreUnit<>(CommandPO.ENTITY_NAME);
		defaultRoles = new DataStoreUnit<>(DefaultRolePO.ENTITY_NAME);
		globalSettings = new DataStoreUnit<>(GlobalSettingsPO.ENTITY_NAME);
		guilds = new DataStoreUnit<>(GuildPO.ENTITY_NAME);
		guildSettings = new DataStoreUnit<>(GuildSettingsPO.ENTITY_NAME);
		localisations = new DataStoreUnit<>(LocalisationPO.ENTITY_NAME);
		moduleSettings = new DataStoreUnit<>(ModuleSettingsPO.ENTITY_NAME);
		owners = new DataStoreUnit<>(OwnerPO.ENTITY_NAME);
		roleMembers = new DataStoreUnit<>(RoleMemberPO.ENTITY_NAME);
		roles = new DataStoreUnit<>(RolePO.ENTITY_NAME);
		snowflakeTypes = new DataStoreUnit<>(SnowflakeTypePO.ENTITY_NAME);
		tokens = new DataStoreUnit<>(TokenPO.ENTITY_NAME);
		users = new DataStoreUnit<>(UserPO.ENTITY_NAME);
	}

	public static DataStoreUnit<BindingPO, Binding> bindings() {
		return bindings;
	}

	public static DataStoreUnit<BindingTypePO, BindingType> bindingTypes() {
		return bindingTypes;
	}

	public static DataStoreUnit<CommandMetricsPO, CommandMetrics> commandMetrics() {
		return commandMetrics;
	}

	public static DataStoreUnit<CommandPO, Command> commands() {
		return commands;
	}

	public static DataStoreUnit<DefaultRolePO, DefaultRole> defaultRoles() {
		return defaultRoles;
	}

	public static DataStoreUnit<GlobalSettingsPO, GlobalSettings> globalSettings() {
		return globalSettings;
	}

	public static DataStoreUnit<GuildPO, Guild> guilds() {
		return guilds;
	}

	public static DataStoreUnit<GuildSettingsPO, GuildSettings> guildSettings() {
		return guildSettings;
	}

	public static DataStoreUnit<LocalisationPO, Localisation> localisations() {
		return localisations;
	}

	public static DataStoreUnit<ModuleSettingsPO, ModuleSettings> moduleSettings() {
		return moduleSettings;
	}

	public static DataStoreUnit<OwnerPO, Owner> owners() {
		return owners;
	}

	public static DataStoreUnit<RoleMemberPO, RoleMember> roleMembers() {
		return roleMembers;
	}

	public static DataStoreUnit<RolePO, Role> roles() {
		return roles;
	}

	public static DataStoreUnit<SnowflakeTypePO, SnowflakeType> snowflakeTypes() {
		return snowflakeTypes;
	}

	public static DataStoreUnit<TokenPO, Token> tokens() {
		return tokens;
	}

	public static DataStoreUnit<UserPO, User> users() {
		return users;
	}
}
