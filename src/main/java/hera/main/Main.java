package hera.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import hera.events.AutoAssignRole;
import hera.events.MessageReceivedEventHandler;
import hera.misc.ClientManager;
import hera.misc.ProjectInitalizer;
import hera.misc.SingletonInstancer;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;

public class Main {
	
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		LOG.debug("Start of: Main.main");
		LOG.info("Starting Hera...");
		
		LOG.info("Starting project initialization");
		ProjectInitalizer projectInitalizer = new ProjectInitalizer();
		
		LOG.info("Initialize properties");
		projectInitalizer.initalizeProperties();
		LOG.info("Properties initialized");
		
		LOG.info("Create and login client instance");
		ClientManager cm = ClientManager.getInstance();
		
		try {
			LOG.info("Waiting for login to finish");
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			LOG.error("Login failed");
			LOG.error(e1.getMessage() + " : " + e1.getCause());
		}
		LOG.info("Login successful");
		
		LOG.info("Initialize channel bindings");
		projectInitalizer.initalizeBindings(cm.getiDiscordClient().getGuilds().get(0));
		LOG.info("Channel bindings initialized");

		LOG.info("Initialize YouTube properties");
		projectInitalizer.initializeYoutubeProperties();
		LOG.info("YouTube properties initialized");
		
		LOG.info("Register audio sources");
		AudioPlayerManager apm = SingletonInstancer.getAPMInstance();
		AudioSourceManagers.registerRemoteSources(apm);
		AudioSourceManagers.registerLocalSource(apm);
		LOG.info("Audio sources registered");
		
		LOG.info("Register event listeners");
		EventDispatcher ed = cm.getiDiscordClient().getDispatcher();
		ed.registerListener(new AutoAssignRole());
		ed.registerListener(new MessageReceivedEventHandler());
		LOG.info("Event listeners registered");
		
		LOG.info("Setting discord presence");
		cm.getiDiscordClient().changePresence(StatusType.ONLINE, ActivityType.WATCHING, "over you ಠ_ಠ");
		LOG.info("Discord presence set");
		
		LOG.info("Project initialization finished");
		LOG.info("Hera is now ready to use");
		LOG.debug("End of: Main.main");
	}
}