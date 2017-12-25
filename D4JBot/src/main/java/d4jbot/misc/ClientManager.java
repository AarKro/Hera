package d4jbot.misc;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class ClientManager {
	
	private String token;
	private IDiscordClient iDiscordClient;

	// default constructor
	public ClientManager() { }
	
	// constructor
	public ClientManager(String token) {
		this.token = token;
		this.iDiscordClient = createClient(token);
	}
	
	public IDiscordClient createClient(String token) { // Returns a new instance of the Discord client
		
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        
        try {
            return clientBuilder.login(); // Creates the client instance and logs the client in
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
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
