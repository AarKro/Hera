package d4jbot.main;

import d4jbot.events.AutoAssignRole;
import d4jbot.events.Begone;
import d4jbot.events.Bind;
import d4jbot.events.End;
import d4jbot.events.Flip;
import d4jbot.events.Help;
import d4jbot.events.Motd;
import d4jbot.events.No;
import d4jbot.events.Play;
import d4jbot.events.Report;
import d4jbot.events.Teams;
import d4jbot.events.Version;
import d4jbot.events.Vote;
import d4jbot.events.Yes;
import d4jbot.misc.AudioQueueManager;
import d4jbot.misc.ClientManager;
import d4jbot.misc.MessageOfTheDayManager;
import d4jbot.misc.MessageSender;
import d4jbot.misc.VoteManager;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;

public class Main {
	
	public static void main(String[] args) {
		
		// to add Hera to a server: https://discordapp.com/oauth2/authorize?&client_id=398542528581861386&scope=bot&permissions=0
		// ClientManager cm = new ClientManager("Mzk4NTQyNTI4NTgxODYxMzg2.DTAPaA.oHpgHwl6GzoVpFWBbFXsCuccF4U");

		// to add Hera dev to a server: https://discordapp.com/oauth2/authorize?&client_id=398019889417420802&scope=bot&permissions=0
		ClientManager cm = new ClientManager("Mzk4MDE5ODg5NDE3NDIwODAy.DS6Qgw.tIp07lDjYYZZLqVjqjKI6PyNAgc");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		MessageSender ms = new MessageSender();
		VoteManager vm = new VoteManager();
		AudioQueueManager aqm = new AudioQueueManager(cm.getiDiscordClient().getGuilds().get(0), ms);
		MessageOfTheDayManager motdm = new MessageOfTheDayManager(ms, cm);
		
		EventDispatcher ed = cm.getiDiscordClient().getDispatcher();
		ed.registerListener(new Bind(ms));
		ed.registerListener(new Report(ms));
		ed.registerListener(new Flip(ms));
		ed.registerListener(new Teams(ms));
		ed.registerListener(new Help(ms));
		ed.registerListener(new Vote(ms, vm));
		ed.registerListener(new Yes(ms, vm));
		ed.registerListener(new No(ms, vm));
		ed.registerListener(new End(ms, vm));
		ed.registerListener(new Version(ms));
		ed.registerListener(new Begone(ms));
		ed.registerListener(new AutoAssignRole());
		ed.registerListener(new Motd(ms, motdm));
		ed.registerListener(new Play(ms, aqm));
		
		cm.getiDiscordClient().changePresence(StatusType.ONLINE, ActivityType.WATCHING, "over you ಠ_ಠ");
	}
}
