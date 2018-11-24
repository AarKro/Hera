package hera.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import hera.constants.BotConstants;
import hera.eventSupplements.MessageSender;
import hera.music.GuildAudioPlayerManager;
import hera.propertyHandling.PropertiesHandler;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class SaveToPlaylist implements Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(SaveToPlaylist.class);
	
	private static SaveToPlaylist instance;

	public static SaveToPlaylist getInstance() {
		if (instance == null)
			instance = new SaveToPlaylist();
		return instance;
	}

	private MessageSender ms;
	private GuildAudioPlayerManager gapm;
	
	private SaveToPlaylist() {
		this.ms = MessageSender.getInstance();
		this.gapm = GuildAudioPlayerManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: SaveToPlaylist.execute");
			
		String[] args = e.getMessage().getContent().split(" ");
		if (args.length == 2) {
			PropertiesHandler playlists = new PropertiesHandler(BotConstants.PLAYLISTS_PROPERTY_LOCATION);
			playlists.load();
			
			if(playlists.getProperty(args[1]) != null) {
				ms.sendMessage(e.getChannel(), "", "Name already in use, to see current playlists use $playlists");
				LOG.debug(e.getAuthor() + " Name already in use, ending now");
				return;
			} else {
				AudioTrack[] tracks = gapm.getGuildAudioPlayer(e.getGuild()).getScheduler().getQueue();
				AudioTrack currentTrack = gapm.getGuildAudioPlayer(e.getGuild()).player.getPlayingTrack();
				String playlist = "";
				
				if(tracks.length == 0 || tracks == null) {
					ms.sendMessage(e.getChannel(), "", "Queue is empty, can't create empty playlist");
					LOG.debug(e.getAuthor() + " tried to create empty playlist");
					return;
				}
				
				if(currentTrack != null) {
					playlist += currentTrack.getInfo().identifier + ";";
				}
				
				for(AudioTrack track : tracks) {
					playlist += track.getInfo().identifier + ";";
				}	
				
				playlists.setProperty(args[1], playlist);
				playlists.save("playlist created");
				
				ms.sendMessage(e.getChannel(), "", "Playlist created");
			}		
			
			LOG.info(e.getAuthor() + " has created playlist " + args[1]);
			
		} else {
			ms.sendMessage(e.getChannel(), "", "Invalid usage of $savetoplaylist .\nSyntax: $savetoplaylist name");
			LOG.debug(e.getAuthor() + " used command savetoplaylist wrong");
		}
		
		LOG.debug("End of: SaveToPlaylist.execute");
	}
}
