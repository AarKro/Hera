package d4jbot.main;

import d4jbot.events.Bind;
import d4jbot.events.Flip;
import d4jbot.events.Help;
import d4jbot.events.Report;
import d4jbot.events.Teams;
import sx.blah.discord.api.events.EventDispatcher;

public class Main {
	
	public static void main(String[] args) {
		// to add bot to a server: https://discordapp.com/oauth2/authorize?&client_id=389175473030037504&scope=bot&permissions=0
		
		ClientManager cm = new ClientManager("Mzg5MTc1NDczMDMwMDM3NTA0.DR79nw.ylrL1aAd-LsQdpHAbN2kcgsHIuM");
		
		EventDispatcher ed = cm.getiDiscordClient().getDispatcher();
		ed.registerListener(new Bind());
		ed.registerListener(new Report());
		ed.registerListener(new Flip());
		ed.registerListener(new Teams());
		ed.registerListener(new Help());
	}
	
}
