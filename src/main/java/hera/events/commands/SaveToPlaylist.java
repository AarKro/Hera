package hera.events.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import hera.constants.BotConstants;
import hera.events.Command;
import hera.music.GuildAudioPlayerManager;
import hera.propertyHandling.PropertiesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class SaveToPlaylist extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(SaveToPlaylist.class);

    private static SaveToPlaylist instance;

    public static SaveToPlaylist getInstance() {
        if (instance == null)
            instance = new SaveToPlaylist();
        return instance;
    }

    private hera.eventSupplements.MessageSender ms;
    private GuildAudioPlayerManager gapm;

    private SaveToPlaylist() {
        super(null, 1, true);
        this.ms = hera.eventSupplements.MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: SaveToPlaylist.execute");

        PropertiesHandler playlists = new PropertiesHandler(BotConstants.PLAYLISTS_PROPERTY_LOCATION);
        playlists.load();

        if(playlists.getProperty(params[0]) != null) {
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

            playlists.setProperty(params[0], playlist);
            playlists.save("playlist created");

            ms.sendMessage(e.getChannel(), "", "Playlist created");
        }

        LOG.info(e.getAuthor() + " has created playlist " + params[0]);

        LOG.debug("End of: SaveToPlaylist.execute");
    }
}
