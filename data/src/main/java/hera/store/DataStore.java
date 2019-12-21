package hera.store;

import hera.database.entities.mapped.Owner;
import hera.database.entities.mapped.User;
import hera.database.entities.persistence.OwnerPO;
import hera.database.entities.persistence.UserPO;
import hera.store.unit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStore {

	private static final Logger LOG = LoggerFactory.getLogger(DataStore.class);

	public static final DataStore STORE = new DataStore();

	private AliasAccessUnit aliases;

	private BindingAccessUnit bindings;

	private MetricAccessUnit metrics;

	private CommandAccessUnit commands;

	private DefaultRoleAccessUnit defaultRoles;

	private GlobalSettingAccessUnit globalSettings;

	private GuildSettingAccessUnit guildSettings;

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
		aliases = new AliasAccessUnit();
		bindings = new BindingAccessUnit();
		metrics = new MetricAccessUnit();
		commands = new CommandAccessUnit();
		defaultRoles = new DefaultRoleAccessUnit();
		globalSettings = new GlobalSettingAccessUnit();
		guildSettings = new GuildSettingAccessUnit();
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

	public AliasAccessUnit alias() { return aliases;}

	public BindingAccessUnit bindings() {
		return bindings;
	}

	public MetricAccessUnit metrics() {
		return metrics;
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
