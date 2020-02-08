package hera.store;

import hera.database.entities.Guild;
import hera.database.entities.Metric;
import hera.database.entities.Owner;
import hera.database.entities.User;
import hera.store.unit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStore {

	private static final Logger LOG = LoggerFactory.getLogger(DataStore.class);

	public static final DataStore STORE = new DataStore();

	private AliasAccessUnit aliases;

	private BindingAccessUnit bindings;

	private CommandAccessUnit commands;

	private DefaultRoleAccessUnit defaultRoles;

	private GlobalSettingAccessUnit globalSettings;

	private GuildSettingAccessUnit guildSettings;

	private LocalisationAccessUnit localisations;

	private ModuleSettingsAccessUnit moduleSettings;

	private RoleMemberAccessUnit roleMembers;

	private RoleAccessUnit roles;

	private TokenAccessUnit tokens;

	private StorageAccessUnit<Guild> guilds;

	private StorageAccessUnit<Owner> owners;

	private StorageAccessUnit<Metric> metrics;

	private StorageAccessUnit<User> users;

	private DataStore() {
	}

	public void initialise() {
		LOG.info("Initialising DataStore");
		aliases = new AliasAccessUnit();
		bindings = new BindingAccessUnit();
		commands = new CommandAccessUnit();
		defaultRoles = new DefaultRoleAccessUnit();
		globalSettings = new GlobalSettingAccessUnit();
		guildSettings = new GuildSettingAccessUnit();
		localisations = new LocalisationAccessUnit();
		moduleSettings = new ModuleSettingsAccessUnit();
		roleMembers = new RoleMemberAccessUnit();
		roles = new RoleAccessUnit();
		tokens = new TokenAccessUnit();
		guilds = new StorageAccessUnit<>(Guild.class);
		owners = new StorageAccessUnit<>(Owner.class);
		metrics = new StorageAccessUnit<>(Metric.class);
		users = new StorageAccessUnit<>(User.class);
		LOG.info("DataStore initialised");
	}

	public AliasAccessUnit aliases() { return aliases;}

	public BindingAccessUnit bindings() {
		return bindings;
	}

	public CommandAccessUnit commands() {
		return commands;
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

	public StorageAccessUnit<Owner> owners() {
		return owners;
	}

	public StorageAccessUnit<Metric> metrics() {
		return metrics;
	}

	public StorageAccessUnit<User> users() {
		return users;
	}
}
