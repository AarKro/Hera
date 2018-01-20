package d4jbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import d4jbot.enums.BoundChannel;
import d4jbot.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;

public class AudioLoadResultManager implements AudioLoadResultHandler {

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
	    ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Adding to queue:\n" + track.getInfo().title);
		play(event.getGuild(), musicManager, track);
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		int totalDuration = 0;
		for(AudioTrack track : playlist.getTracks()) {
			totalDuration += track.getDuration();
		}

		playPlaylist(event.getGuild(), musicManager, playlist);
		
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), 
				"Adding to queue:\nPlaylist " + playlist.getName() + "\n\nTotal songs: " + playlist.getTracks().size() + " | Total duration " + getFormattedTime(totalDuration));
	}

	@Override
	public void noMatches() {
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Nothing found by:\n" + trackUrl);
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), "Could not play:\n" + exception.getMessage());
	}

	private void play(IGuild guild, GuildMusicManager musicManager, AudioTrack track) {
		event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel().join();
		musicManager.scheduler.queue(track);
	}

	private void playPlaylist(IGuild guild, GuildMusicManager musicManager, AudioPlaylist playlist) {
		event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel().join();
		musicManager.scheduler.queuePlaylist(playlist);
	}
	
	private String getFormattedTime(long milliseconds) {
		return String.format("%02d:%02d:%02d", (milliseconds / (1000 * 60 * 60)) % 24, (milliseconds / (1000 * 60)) % 60, (milliseconds / 1000) %60);
	}
}
