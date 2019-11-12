package hera.store;

import hera.database.entity.mapped.*;
import hera.database.entity.persistence.*;
import hera.store.unit.CommandMetricsStoreUnit;
import hera.store.unit.DataStoreUnit;

public class DataStore {

	private static DataStore instance;

	private DataStoreUnit<BindingPO, Binding> bindings;

	private DataStoreUnit<BindingTypePO, BindingType> bindingTypes;

	private CommandMetricsStoreUnit commandMetrics;

	private DataStoreUnit<CommandPO, Command> commands;

	private DataStoreUnit<DefaultRolePO, DefaultRole> defaultRoles;

	private DataStoreUnit<GlobalSettingsPO, GlobalSettings> globalSettings;

	private DataStoreUnit<GuildPO, Guild> guilds;

	private DataStoreUnit<GuildSettingsPO, GuildSettings> guildSettings;

	private DataStoreUnit<LocalisationPO, Localisation> localisations;

	private DataStoreUnit<ModuleSettingsPO, ModuleSettings> moduleSettings;

	private DataStoreUnit<OwnerPO, Owner> owners;

	private DataStoreUnit<RoleMemberPO, RoleMember> roleMembers;

	private DataStoreUnit<RolePO, Role> roles;

	private DataStoreUnit<SnowflakeTypePO, SnowflakeType> snowflakeTypes;

	private DataStoreUnit<TokenPO, Token> tokens;

	private DataStoreUnit<UserPO, User> users;

	private DataStore() {
	}

	public static DataStore getInstance() {
		if(instance == null) {
			instance = new DataStore();
		}

		return instance;
	}

	public void initialize() {
		bindings = new DataStoreUnit<>(BindingPO.ENTITY_NAME);
		bindingTypes = new DataStoreUnit<>(BindingTypePO.ENTITY_NAME);
		commandMetrics = new CommandMetricsStoreUnit(CommandMetricsPO.ENTITY_NAME);
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

	public DataStoreUnit<BindingPO, Binding> bindings() {
		return bindings;
	}

	public DataStoreUnit<BindingTypePO, BindingType> bindingTypes() {
		return bindingTypes;
	}

	public CommandMetricsStoreUnit commandMetrics() {
		return commandMetrics;
	}

	public DataStoreUnit<CommandPO, Command> commands() {
		return commands;
	}

	public DataStoreUnit<DefaultRolePO, DefaultRole> defaultRoles() {
		return defaultRoles;
	}

	public DataStoreUnit<GlobalSettingsPO, GlobalSettings> globalSettings() {
		return globalSettings;
	}

	public DataStoreUnit<GuildPO, Guild> guilds() {
		return guilds;
	}

	public DataStoreUnit<GuildSettingsPO, GuildSettings> guildSettings() {
		return guildSettings;
	}

	public DataStoreUnit<LocalisationPO, Localisation> localisations() {
		return localisations;
	}

	public DataStoreUnit<ModuleSettingsPO, ModuleSettings> moduleSettings() {
		return moduleSettings;
	}

	public DataStoreUnit<OwnerPO, Owner> owners() {
		return owners;
	}

	public DataStoreUnit<RoleMemberPO, RoleMember> roleMembers() {
		return roleMembers;
	}

	public DataStoreUnit<RolePO, Role> roles() {
		return roles;
	}

	public DataStoreUnit<SnowflakeTypePO, SnowflakeType> snowflakeTypes() {
		return snowflakeTypes;
	}

	public DataStoreUnit<TokenPO, Token> tokens() {
		return tokens;
	}

	public DataStoreUnit<UserPO, User> users() {
		return users;
	}
}
