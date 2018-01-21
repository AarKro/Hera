package d4jbot.main;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import d4jbot.events.AutoAssignRole;
import d4jbot.events.Begone;
import d4jbot.events.Bind;
import d4jbot.events.End;
import d4jbot.events.Flip;
import d4jbot.events.Help;
import d4jbot.events.Join;
import d4jbot.events.Leave;
import d4jbot.events.Motd;
import d4jbot.events.No;
import d4jbot.events.Np;
import d4jbot.events.Play;
import d4jbot.events.Queue;
import d4jbot.events.Report;
import d4jbot.events.Shame;
import d4jbot.events.Teams;
import d4jbot.events.Version;
import d4jbot.events.Volume;
import d4jbot.events.Vote;
import d4jbot.events.Yes;
import d4jbot.misc.ClientManager;
import d4jbot.misc.MessageOfTheDayManager;
import d4jbot.misc.MessageSender;
import d4jbot.misc.ProjectInitalizer;
import d4jbot.misc.VoteManager;
import d4jbot.music.GuildAudioPlayerManager;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;

public class Main {
	
	public static void main(String[] args) {
		
		ProjectInitalizer projectInitalizer = new ProjectInitalizer();
		projectInitalizer.initalizeProperties();
		ClientManager cm = projectInitalizer.getClientmanager();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		projectInitalizer.initalizeBindings(cm.getiDiscordClient().getGuilds().get(0));
		
		MessageSender ms = new MessageSender();
		VoteManager vm = new VoteManager();
						
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
		ed.registerListener(new Shame(ms));
		ed.registerListener(new AutoAssignRole());
		
		MessageOfTheDayManager motdm = new MessageOfTheDayManager(ms, cm);
		ed.registerListener(new Motd(ms, motdm));
		
		AudioPlayerManager apm = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(apm);
		AudioSourceManagers.registerLocalSource(apm);
		GuildAudioPlayerManager gapm = new GuildAudioPlayerManager(apm, ms);
		ed.registerListener(new Play(ms, gapm));
		ed.registerListener(new Queue(ms, gapm));
		ed.registerListener(new Join());
		ed.registerListener(new Leave());
		ed.registerListener(new Np(ms, gapm));
		ed.registerListener(new Volume(ms, gapm));
		//ed.registerListener(new Lq(ms, gapm));
		
		cm.getiDiscordClient().changePresence(StatusType.ONLINE, ActivityType.WATCHING, "over you ಠ_ಠ");
	}
}