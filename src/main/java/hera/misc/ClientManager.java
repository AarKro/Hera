package hera.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.constants.BotConstants;
import hera.enums.BotSettings;
import hera.main.Main;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class ClientManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(ClientManager.class);
	
	private static ClientManager instance;
	
	public static ClientManager getInstance() {
		if (instance == null)  {
			PropertiesHandler propHandler = new PropertiesHandler(BotConstants.CLIENT_PROPERTY_LOCATION);
			propHandler.load();
			String propertyName = BotSettings.BOT_DEV_STATUS.getPropertyValue() + "." + BotConstants.CLIENT_TOKEN_PROPERTY_NAME;
			String token = propHandler.getProperty(propertyName);
			instance = new ClientManager(token);
		}
		return instance;
	}
	
	
	private String token;
	private IDiscordClient iDiscordClient;
	
	// constructor
	private ClientManager(String token) {
		this.token = token;
		this.iDiscordClient = createClient(token);
	}
	
	public IDiscordClient createClient(String token) { // Returns a new instance of the Discord client
		LOG.debug("Start of: ClientManager.createClient");
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        
        try {
        	LOG.info("Creating client instance and loging it in");
            return clientBuilder.login(); // Creates the client instance and logs the client in
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
        	LOG.error("There was a problem building the client");
        	LOG.error(e.getMessage() + " : " + e.getCause());
        	return null;
        } finally {
        	LOG.debug("End of: ClientManager.createClient");
        }
    }

	// getters & setters
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public IDiscordClient getiDiscordClient() {
		return iDiscordClient;
	}

	public void setiDiscordClient(IDiscordClient iDiscordClient) {
		this.iDiscordClient = iDiscordClient;
	}
}
