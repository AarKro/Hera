package hera.store;

import hera.database.entities.mapped.*;
import hera.database.entities.persistence.*;
import hera.store.unit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStore {

	private static final Logger LOG = LoggerFactory.getLogger(DataStore.class);

	public static final DataStore STORE = new DataStore();

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

	private GuildAccessUnit guilds;

	private StorageAccessUnit<OwnerPO, Owner> owners;

	private StorageAccessUnit<UserPO, User> users;

	private DataStore() {
	}

	public void initialise() {
		LOG.info("Initialising DataStore");
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
		guilds = new GuildAccessUnit();
		owners = new StorageAccessUnit<>(OwnerPO.ENTITY_NAME);
		users = new StorageAccessUnit<>(UserPO.ENTITY_NAME);
		LOG.info("DataStore initialised");
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

	public GuildAccessUnit guilds() {
		return guilds;
	}

	public StorageAccessUnit<OwnerPO, Owner> owners() {
		return owners;
	}

	public StorageAccessUnit<UserPO, User> users() {
		return users;
	}
}
