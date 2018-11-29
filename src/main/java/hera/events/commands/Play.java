package hera.events.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import hera.events.Command;
import hera.instanceManagement.SingletonInstancer;
import hera.music.AudioLoadResultManager;
import hera.music.GuildAudioPlayerManager;
import hera.music.GuildMusicManager;
import hera.youtubeAPI.YoutubeAPIHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Play extends Command {

    private static final Logger LOG = LoggerFactory.getLogger(Play.class);

    private static Play instance;

    public static Play getInstance() {
        if (instance == null)
            instance = new Play();
        return instance;
    }

    private hera.eventSupplements.MessageSender ms;
    private AudioPlayerManager apm;
    private GuildAudioPlayerManager gapm;

    // constructor
    private Play() {
        super(null, 1, false);
        this.ms = hera.eventSupplements.MessageSender.getInstance();
        this.gapm = GuildAudioPlayerManager.getInstance();
        this.apm = SingletonInstancer.getAPMInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: Play.execute");
        String songURL = "";
        if (params[0].matches("(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")) {
            songURL = params[0];
            LOG.debug("Input matches an URL. URL: " + songURL);
        } else {
            LOG.debug("Input consists of keywords to search with on YouTube");
            songURL = YoutubeAPIHandler.getInstance().getYoutubeVideoFromKeyword(params[0]);
            LOG.debug("URL received after searching on YouTube: " + songURL);
        }

        GuildMusicManager musicManager = gapm.getGuildAudioPlayer(e.getGuild());
        LOG.info(e.getAuthor() + " queued song: " + songURL);
        apm.loadItemOrdered(musicManager, songURL, new AudioLoadResultManager(e, songURL, ms, musicManager));
        LOG.debug("End of: Play.execute");
    }
}
