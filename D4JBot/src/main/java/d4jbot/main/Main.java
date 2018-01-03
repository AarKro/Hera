package d4jbot.main;

import d4jbot.events.AutoAssignRole;
import d4jbot.events.Begone;
import d4jbot.events.Bind;
import d4jbot.events.End;
import d4jbot.events.Flip;
import d4jbot.events.Help;
import d4jbot.events.Motd;
import d4jbot.events.N;
import d4jbot.events.Report;
import d4jbot.events.Teams;
import d4jbot.events.Version;
import d4jbot.events.Vote;
import d4jbot.events.Y;
import d4jbot.misc.ChannelBinder;
import d4jbot.misc.ClientManager;
import d4jbot.misc.MessageOfTheDayManager;
import d4jbot.misc.MessageSender;
import d4jbot.misc.VoteManager;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;

public class Main {
	
	public static void main(String[] args) {
		
		// to add Hera to a server: https://discordapp.com/oauth2/authorize?&client_id=389175473030037504&scope=bot&permissions=0
		// ClientManager cm = new ClientManager("Mzg5MTc1NDczMDMwMDM3NTA0.DR79nw.ylrL1aAd-LsQdpHAbN2kcgsHIuM");

		// to add Hera dev to a server: https://discordapp.com/oauth2/authorize?&client_id=398019889417420802&scope=bot&permissions=0
		ClientManager cm = new ClientManager("Mzk4MDE5ODg5NDE3NDIwODAy.DS6Qgw.tIp07lDjYYZZLqVjqjKI6PyNAgc");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		MessageSender ms = new MessageSender();
		ChannelBinder cb = new ChannelBinder(ms);
		VoteManager vm = new VoteManager();
		//MessageOfTheDayManager motdm = new MessageOfTheDayManager(ms, cm);
		
		EventDispatcher ed = cm.getiDiscordClient().getDispatcher();
		ed.registerListener(new Bind(ms, cb));
		ed.registerListener(new Report(ms));
		ed.registerListener(new Flip(ms));
		ed.registerListener(new Teams(ms));
		ed.registerListener(new Help(ms));
		ed.registerListener(new Vote(ms, vm));
		ed.registerListener(new Y(ms, vm));
		ed.registerListener(new N(ms, vm));
		ed.registerListener(new End(ms, vm));
		ed.registerListener(new Version(ms));
		ed.registerListener(new Begone(ms));
		ed.registerListener(new AutoAssignRole());
		//ed.registerListener(new Motd(ms, motdm));
		
		cm.getiDiscordClient().changePresence(StatusType.ONLINE, ActivityType.WATCHING, "over you ಠ_ಠ");
	}
}
