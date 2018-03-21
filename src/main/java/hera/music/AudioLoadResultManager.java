package hera.music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import hera.enums.BoundChannel;
import hera.events.Yes;
import hera.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;

public class AudioLoadResultManager implements AudioLoadResultHandler {

	private static final Logger LOG = LoggerFactory.getLogger(AudioLoadResultManager.class);
	
	private MessageReceivedEvent event;
	private String trackUrl;
	private MessageSender ms;
	private GuildMusicManager musicManager;

	// default constructor
	public AudioLoadResultManager() {
	}

	// constructor
	public AudioLoadResultManager(MessageReceivedEvent event, String trackUrl, MessageSender ms, GuildMusicManager musicManager) {
		this.event = event;
		this.trackUrl = trackUrl;
		this.ms = ms;
		this.musicManager = musicManager;
	}

	@Override
	public void trackLoaded(AudioTrack track) {
		LOG.debug("Start of: AudioLoadResultManager.trackLoaded");
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Adding to queue:\n" + track.getInfo().title + " by " + track.getInfo().author + " | " + getFormattedTime(track.getDuration()));
		play(event.getGuild(), musicManager, track);
		LOG.debug("End of: AudioLoadResultManager.trackLoaded");
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		LOG.debug("Start of: AudioLoadResultManager.playlistLoaded");
		int totalDuration = 0;
		System.out.println(playlist.getTracks().size());
		for(AudioTrack track : playlist.getTracks()) {
			totalDuration += track.getDuration();
		}

		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), 
				"Adding to queue:\nPlaylist " + playlist.getName() + "\n\nTotal songs: " + playlist.getTracks().size() + " | Total duration " + getFormattedTime(totalDuration));
		LOG.debug("Adding playlist to queue");
		
		playPlaylist(event.getGuild(), musicManager, playlist);
		
		LOG.debug("End of: AudioLoadResultManager.playlistLoaded");
	}

	@Override
	public void noMatches() {
		LOG.debug("Start of: AudioLoadResultManager.noMatches");
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Nothing found by:\n" + trackUrl);
		LOG.info( "Nothing found by: " + trackUrl);
		LOG.debug("End of: AudioLoadResultManager.noMatches");
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		LOG.debug("Start of: AudioLoadResultManager.loadFailed");
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Could not play:\n" + exception.getMessage());
		LOG.error("Could not play");
		LOG.error(exception.getMessage() + " : " + exception.getCause());
		LOG.debug("End of: AudioLoadResultManager.loadFailed");
	}

	private void play(IGuild guild, GuildMusicManager musicManager, AudioTrack track) {
		LOG.debug("Start of: AudioLoadResultManager.play");
		event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel().join();
		musicManager.scheduler.queue(track);
		LOG.info("Queued track " + track.getInfo().title + " by " + track.getInfo().author);
		LOG.debug("End of: AudioLoadResultManager.play");
	}

	private void playPlaylist(IGuild guild, GuildMusicManager musicManager, AudioPlaylist playlist) {
		LOG.debug("Start of: AudioLoadResultManager.playPlaylist");
		event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel().join();
		musicManager.scheduler.queuePlaylist(playlist);
		LOG.info("Queued playlist");
		LOG.debug("End of: AudioLoadResultManager.playPlaylist");
	}
	
	private String getFormattedTime(long milliseconds) {
		LOG.debug("Start of: AudioLoadResultManager.getFormattedTime");
		LOG.debug("End of: AudioLoadResultManager.getFormattedTime");
		return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24, (milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) %60);
	}
}
