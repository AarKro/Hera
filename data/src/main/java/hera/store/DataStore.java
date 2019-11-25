package hera.store;

import hera.database.entity.mapped.*;
import hera.database.entity.persistence.*;
import hera.store.unit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStore {

	private static final Logger LOG = LoggerFactory.getLogger(DataStore.class);

	private static DataStore instance;

	private BindingAccessUnit bindings;

	private CommandMetricsAccessUnit commandMetrics;

	private CommandAccessUnit commands;

	private DefaultRoleAccessUnit defaultRoles;

	private GlobalSettingsAccessUnit globalSettings;

	private GuildSettingsAccessUnit guildSettings;

	private LocalisationAccessUnit localisations;

	private ModuleSettingsAccessUnit moduleSettings;

	private RoleMemberAccessUnit roleMembers;

	private RoleAccessUnit roles;

	private TokenAccessUnit tokens;

	private StorageAccessUnit<GuildPO, Guild> guilds;

	private StorageAccessUnit<BindingTypePO, BindingType> bindingTypes;

	private StorageAccessUnit<OwnerPO, Owner> owners;

	private StorageAccessUnit<SnowflakeTypePO, SnowflakeType> snowflakeTypes;

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
		bindings = new BindingAccessUnit();
		commandMetrics = new CommandMetricsAccessUnit();
		commands = new CommandAccessUnit();
		defaultRoles = new DefaultRoleAccessUnit();
		globalSettings = new GlobalSettingsAccessUnit();
		guildSettings = new GuildSettingsAccessUnit();
		localisations = new LocalisationAccessUnit();
		moduleSettings = new ModuleSettingsAccessUnit();
		roleMembers = new RoleMemberAccessUnit();
		roles = new RoleAccessUnit();
		tokens = new TokenAccessUnit();
		bindingTypes = new StorageAccessUnit<>(BindingTypePO.ENTITY_NAME);
		guilds = new StorageAccessUnit<>(GuildPO.ENTITY_NAME);
		owners = new StorageAccessUnit<>(OwnerPO.ENTITY_NAME);
		snowflakeTypes = new StorageAccessUnit<>(SnowflakeTypePO.ENTITY_NAME);
		users = new StorageAccessUnit<>(UserPO.ENTITY_NAME);
		LOG.info("DataStore initialized");
	}

	public BindingAccessUnit bindings() {
		return bindings;
	}

	public CommandMetricsAccessUnit commandMetrics() {
		return commandMetrics;
	}

	public CommandAccessUnit commands() {
		return commands;
	}

	public DefaultRoleAccessUnit defaultRoles() {
		return defaultRoles;
	}

	public GlobalSettingsAccessUnit globalSettings() {
		return globalSettings;
	}

	public GuildSettingsAccessUnit guildSettings() {
		return guildSettings;
	}

	public LocalisationAccessUnit localisations() {
		return localisations;
	}

	public ModuleSettingsAccessUnit moduleSettings() {
		return moduleSettings;
	}

	public RoleMemberAccessUnit roleMembers() {
		return roleMembers;
	}

	public RoleAccessUnit roles() {
		return roles;
	}

	public TokenAccessUnit tokens() {
		return tokens;
	}

	public StorageAccessUnit<BindingTypePO, BindingType> bindingTypes() {
		return bindingTypes;
	}

	public StorageAccessUnit<GuildPO, Guild> guilds() {
		return guilds;
	}

	public StorageAccessUnit<OwnerPO, Owner> owners() {
		return owners;
	}

	public StorageAccessUnit<SnowflakeTypePO, SnowflakeType> snowflakeTypes() {
		return snowflakeTypes;
	}

	public StorageAccessUnit<UserPO, User> users() {
		return users;
	}
}
