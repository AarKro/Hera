package hera.music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import sx.blah.discord.handle.audio.AudioEncodingType;
import sx.blah.discord.handle.audio.IAudioProvider;

public class AudioProvider implements IAudioProvider {
	
	private static final Logger LOG = LoggerFactory.getLogger(AudioProvider.class);
	
	private final AudioPlayer audioPlayer;
	private AudioFrame lastFrame;

	public AudioProvider(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	@Override
	public boolean isReady() {
		LOG.debug("Start of: AudioProvider.isReady");
		if (lastFrame == null) {
			lastFrame = audioPlayer.provide();
		}

		LOG.debug("End of: AudioProvider.isReady");
		return lastFrame != null;
	}

	@Override
	public byte[] provide() {
		LOG.debug("Start of: AudioProvider.provide");
		if (lastFrame == null) {
			lastFrame = audioPlayer.provide();
		}

		byte[] data = lastFrame != null ? lastFrame.data : null;
		lastFrame = null;

		LOG.debug("End of: AudioProvider.provide");
		return data;
	}

	@Override
	public int getChannels() {
		return 2;
	}

	@Override
	public AudioEncodingType getAudioEncodingType() {
		return AudioEncodingType.OPUS;
	}
	
	// getters & setters
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
}