package d4jbot.misc;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import d4jbot.enums.BoundChannel;
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
	    ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Adding to queue " + track.getInfo().title);
		play(event.getGuild(), musicManager, track);
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		AudioTrack firstTrack = playlist.getSelectedTrack();

		if (firstTrack == null) {
			firstTrack = playlist.getTracks().get(0);
		}

		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Adding to queue " + firstTrack.getInfo().title + " (first track of playlist "	+ playlist.getName() + ")");

		play(event.getGuild(), musicManager, firstTrack);
	}

	@Override
	public void noMatches() {
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Nothing found by " + trackUrl);
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		ms.sendMessage(BoundChannel.MUSIC.getBoundChannel(), true, "Could not play: " + exception.getMessage());
	}

	private void play(IGuild guild, GuildMusicManager musicManager, AudioTrack track) {
		event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel().join();
		musicManager.scheduler.queue(track);
	}
}
