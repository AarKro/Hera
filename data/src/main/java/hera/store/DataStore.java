package hera.store;

import hera.database.entity.mapped.*;
import hera.database.entity.persistence.*;
import hera.store.unit.CommandMetricsAccessUnit;
import hera.store.unit.StorageAccessUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStore {

	private static final Logger LOG = LoggerFactory.getLogger(DataStore.class);

	private static DataStore instance;

	private StorageAccessUnit<BindingPO, Binding> bindings;

	private StorageAccessUnit<BindingTypePO, BindingType> bindingTypes;

	private CommandMetricsAccessUnit commandMetrics;

	private StorageAccessUnit<CommandPO, Command> commands;

	private StorageAccessUnit<DefaultRolePO, DefaultRole> defaultRoles;

	private StorageAccessUnit<GlobalSettingsPO, GlobalSettings> globalSettings;

	private StorageAccessUnit<GuildPO, Guild> guilds;

	private StorageAccessUnit<GuildSettingsPO, GuildSettings> guildSettings;

	private StorageAccessUnit<LocalisationPO, Localisation> localisations;

	private StorageAccessUnit<ModuleSettingsPO, ModuleSettings> moduleSettings;

	private StorageAccessUnit<OwnerPO, Owner> owners;

	private StorageAccessUnit<RoleMemberPO, RoleMember> roleMembers;

	private StorageAccessUnit<RolePO, Role> roles;

	private StorageAccessUnit<SnowflakeTypePO, SnowflakeType> snowflakeTypes;

	private StorageAccessUnit<TokenPO, Token> tokens;

	private StorageAccessUnit<UserPO, User> users;

	private DataStore() {
	}

	public static DataStore getInstance() {
		if(instance == null) {
			instance = new DataStore();
		}

		return instance;
	}

	public void initialize() {
		LOG.info("Initializing DataStore");
		bindings = new StorageAccessUnit<>(BindingPO.ENTITY_NAME);
		bindingTypes = new StorageAccessUnit<>(BindingTypePO.ENTITY_NAME);
		commandMetrics = new CommandMetricsAccessUnit(CommandMetricsPO.ENTITY_NAME);
		commands = new StorageAccessUnit<>(CommandPO.ENTITY_NAME);
		defaultRoles = new StorageAccessUnit<>(DefaultRolePO.ENTITY_NAME);
		globalSettings = new StorageAccessUnit<>(GlobalSettingsPO.ENTITY_NAME);
		guilds = new StorageAccessUnit<>(GuildPO.ENTITY_NAME);
		guildSettings = new StorageAccessUnit<>(GuildSettingsPO.ENTITY_NAME);
		localisations = new StorageAccessUnit<>(LocalisationPO.ENTITY_NAME);
		moduleSettings = new StorageAccessUnit<>(ModuleSettingsPO.ENTITY_NAME);
		owners = new StorageAccessUnit<>(OwnerPO.ENTITY_NAME);
		roleMembers = new StorageAccessUnit<>(RoleMemberPO.ENTITY_NAME);
		roles = new StorageAccessUnit<>(RolePO.ENTITY_NAME);
		snowflakeTypes = new StorageAccessUnit<>(SnowflakeTypePO.ENTITY_NAME);
		tokens = new StorageAccessUnit<>(TokenPO.ENTITY_NAME);
		users = new StorageAccessUnit<>(UserPO.ENTITY_NAME);
		LOG.info("DataStore initialized");
	}

	public StorageAccessUnit<BindingPO, Binding> bindings() {
		return bindings;
	}

	public StorageAccessUnit<BindingTypePO, BindingType> bindingTypes() {
		return bindingTypes;
	}

	public CommandMetricsAccessUnit commandMetrics() {
		return commandMetrics;
	}

	public StorageAccessUnit<CommandPO, Command> commands() {
		return commands;
	}

	public StorageAccessUnit<DefaultRolePO, DefaultRole> defaultRoles() {
		return defaultRoles;
	}

	public StorageAccessUnit<GlobalSettingsPO, GlobalSettings> globalSettings() {
		return globalSettings;
	}

	public StorageAccessUnit<GuildPO, Guild> guilds() {
		return guilds;
	}

	public StorageAccessUnit<GuildSettingsPO, GuildSettings> guildSettings() {
		return guildSettings;
	}

	public StorageAccessUnit<LocalisationPO, Localisation> localisations() {
		return localisations;
	}

	public StorageAccessUnit<ModuleSettingsPO, ModuleSettings> moduleSettings() {
		return moduleSettings;
	}

	public StorageAccessUnit<OwnerPO, Owner> owners() {
		return owners;
	}

	public StorageAccessUnit<RoleMemberPO, RoleMember> roleMembers() {
		return roleMembers;
	}

	public StorageAccessUnit<RolePO, Role> roles() {
		return roles;
	}

	public StorageAccessUnit<SnowflakeTypePO, SnowflakeType> snowflakeTypes() {
		return snowflakeTypes;
	}

	public StorageAccessUnit<TokenPO, Token> tokens() {
		return tokens;
	}

	public StorageAccessUnit<UserPO, User> users() {
		return users;
	}
}
