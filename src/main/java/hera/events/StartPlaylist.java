package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import hera.constants.BotConstants;
import hera.eventSupplements.MessageSender;
import hera.instanceManagement.SingletonInstancer;
import hera.music.AudioLoadResultManager;
import hera.music.GuildAudioPlayerManager;
import hera.music.GuildMusicManager;
import hera.propertyHandling.PropertiesHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class StartPlaylist implements Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(Alias.class);
	
	private static StartPlaylist instance;

	public static StartPlaylist getInstance() {
		if (instance == null)
			instance = new StartPlaylist();
		return instance;
	}

	private MessageSender ms;
	private AudioPlayerManager apm;
	private GuildAudioPlayerManager gapm;

	private StartPlaylist() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
		this.apm = SingletonInstancer.getAPMInstance();
	}

	public void execute(MessageReceivedEvent e) {
LOG.debug("Start of: StartPlaylist.execute");
		
		String[] args = e.getMessage().getContent().split(" ");
		if (args.length == 2) {
			PropertiesHandler playlists = new PropertiesHandler(BotConstants.PLAYLISTS_PROPERTY_LOCATION);
			playlists.load();
			
			if(playlists.getProperty(args[1]) == null) {
				ms.sendMessage(e.getChannel(), "", "Playlist not found, get existing playlists with $playlists");
				LOG.debug(e.getAuthor() + " tried to start a non-existing playlist");
			} else {
				String playlist = playlists.getProperty(args[1]);
				String[] songs = playlist.split(";");
				
				ms.sendMessage(e.getChannel(), "", "Now queueing playlist: " + args[1]);
				for(String song : songs) {
					GuildMusicManager musicManager = gapm.getGuildAudioPlayer(e.getGuild());
					apm.loadItemOrdered(musicManager, song, new AudioLoadResultManager(e, song, ms, musicManager, true));
				}
			}
		} else {
			ms.sendMessage(e.getChannel(), "", "Invalid usage of $startplaylist .\nSyntax: $startplaylist name");
			LOG.debug(e.getAuthor() + " used command startplaylist wrong");
		}
		
		LOG.debug("End of: StartPlaylist.execute");
	}
}
