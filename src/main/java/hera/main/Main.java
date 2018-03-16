package hera.main;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import hera.events.AutoAssignRole;
import hera.events.HandleMessageRecievedEvent;
import hera.misc.ClientManager;
import hera.misc.ProjectInitalizer;
import hera.misc.SingletonInstancer;
import sx.blah.discord.api.events.EventDispatcher;

public class Main {
	
	public static void main(String[] args) {
		
		ProjectInitalizer projectInitalizer = new ProjectInitalizer();
		projectInitalizer.initalizeProperties();
		ClientManager cm = ClientManager.getInstance();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		projectInitalizer.initalizeBindings(cm.getiDiscordClient().getGuilds().get(0));
		projectInitalizer.initializeYoutubeProperties();
		
		AudioPlayerManager apm = SingletonInstancer.getAPMInstance();
		AudioSourceManagers.registerRemoteSources(apm);
		AudioSourceManagers.registerLocalSource(apm);
		
		EventDispatcher ed = cm.getiDiscordClient().getDispatcher();
		ed.registerListener(new AutoAssignRole());
		ed.registerListener(new HandleMessageRecievedEvent());
		
		//cm.getiDiscordClient().changePresence(StatusType.ONLINE, ActivityType.WATCHING, "over you ಠ_ಠ");
	}
}