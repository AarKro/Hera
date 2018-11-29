package hera.events.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import hera.constants.BotConstants;
import hera.events.Command;
import hera.instanceManagement.SingletonInstancer;
import hera.music.AudioLoadResultManager;
import hera.music.GuildAudioPlayerManager;
import hera.music.GuildMusicManager;
import hera.propertyHandling.PropertiesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class StartPlaylist extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(StartPlaylist.class);

    private static StartPlaylist instance;

    public static StartPlaylist getInstance() {
        if (instance == null)
            instance = new StartPlaylist();
        return instance;
    }

    private hera.eventSupplements.MessageSender ms;
    private AudioPlayerManager apm;
    private GuildAudioPlayerManager gapm;

    private StartPlaylist() {
        super(null, 1, true);
        this.ms = hera.eventSupplements.MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
        this.apm = SingletonInstancer.getAPMInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: StartPlaylist.execute");

        PropertiesHandler playlists = new PropertiesHandler(BotConstants.PLAYLISTS_PROPERTY_LOCATION);
        playlists.load();

        if(playlists.getProperty(params[0]) == null) {
            ms.sendMessage(e.getChannel(), "", "Playlist not found, get existing playlists with $playlists");
            LOG.debug(e.getAuthor() + " tried to start a non-existing playlist");
        } else {
            String playlist = playlists.getProperty(params[0]);
            String[] songs = playlist.split(";");

            ms.sendMessage(e.getChannel(), "", "Now queueing playlist: " + params[0]);
            for(String song : songs) {
                GuildMusicManager musicManager = gapm.getGuildAudioPlayer(e.getGuild());
                apm.loadItemOrdered(musicManager, song, new AudioLoadResultManager(e, song, ms, musicManager, true));
            }
        }

        LOG.debug("End of: StartPlaylist.execute");
    }
}
