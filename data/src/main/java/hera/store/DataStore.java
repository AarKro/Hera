package hera.store;

import hera.database.JPAUtil;
import hera.database.entities.Guild;
import hera.database.entities.Metric;
import hera.database.entities.User;
import hera.store.unit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStore {

	private static final Logger LOG = LoggerFactory.getLogger(DataStore.class);

	public static final DataStore STORE = new DataStore();

	private AliasAccessUnit aliases;

	private BindingAccessUnit bindings;

	private BindingTypeAccessUnit bindingTypes;

	private CommandAccessUnit commands;

	private ConfigFlagAccessUnit configFlags;

	private ConfigFlagTypeAccessUnit configFlagTypes;

	private DefaultRoleAccessUnit defaultRoles;

	private GlobalSettingAccessUnit globalSettings;

	private GuildSettingAccessUnit guildSettings;

	private LocalisationAccessUnit localisations;

	private ModuleSettingsAccessUnit moduleSettings;

	private OwnerAccesUnit owners;

	private RoleMemberAccessUnit roleMembers;

	private RoleAccessUnit roles;

	private TokenAccessUnit tokens;

	private StorageAccessUnit<Guild> guilds;

	private StorageAccessUnit<Metric> metrics;

	private StorageAccessUnit<User> users;

	private DataStore() {
	}

	public void initialise(String dbUser, String dbPassword, String dbUrl) {
		LOG.info("Initialising DataStore");
		JPAUtil.initializeFactory(dbUser, dbPassword, dbUrl);
		aliases = new AliasAccessUnit();
		bindings = new BindingAccessUnit();
		bindingTypes = new BindingTypeAccessUnit();
		commands = new CommandAccessUnit();
		configFlags = new ConfigFlagAccessUnit();
		configFlagTypes = new ConfigFlagTypeAccessUnit();
		defaultRoles = new DefaultRoleAccessUnit();
		globalSettings = new GlobalSettingAccessUnit();
		guildSettings = new GuildSettingAccessUnit();
		localisations = new LocalisationAccessUnit();
		moduleSettings = new ModuleSettingsAccessUnit();
		owners = new OwnerAccesUnit();
		roleMembers = new RoleMemberAccessUnit();
		roles = new RoleAccessUnit();
		tokens = new TokenAccessUnit();
		guilds = new StorageAccessUnit<>(Guild.class);
		metrics = new StorageAccessUnit<>(Metric.class);
		users = new StorageAccessUnit<>(User.class);
		LOG.info("DataStore initialised");
	}

	public AliasAccessUnit aliases() {
		return aliases;
	}

	public BindingAccessUnit bindings() {
		return bindings;
	}

	public BindingTypeAccessUnit bindingTypes() {
		return bindingTypes;
	}

	public CommandAccessUnit commands() {
		return commands;
	}

	public ConfigFlagAccessUnit configFlags() {
		return configFlags;
	}

	public ConfigFlagTypeAccessUnit configFlagTypes() {
		return configFlagTypes;
	}

	public DefaultRoleAccessUnit defaultRoles() {
		return defaultRoles;
	}

	public GlobalSettingAccessUnit globalSettings() {
		return globalSettings;
	}

	public GuildSettingAccessUnit guildSettings() {
		return guildSettings;
	}

	public LocalisationAccessUnit localisations() {
		return localisations;
	}

	public ModuleSettingsAccessUnit moduleSettings() {
		return moduleSettings;
	}

	public OwnerAccesUnit owners() {
		return owners;
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

	public StorageAccessUnit<Guild> guilds() {
		return guilds;
	}

	public StorageAccessUnit<Metric> metrics() {
		return metrics;
	}

	public StorageAccessUnit<User> users() {
		return users;
	}
}
