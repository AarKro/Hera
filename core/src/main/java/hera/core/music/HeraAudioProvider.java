package hera.core.music;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import discord4j.voice.AudioProvider;

import java.nio.ByteBuffer;

public final class HeraAudioProvider extends AudioProvider {

	private final AudioPlayer player;

	private final MutableAudioFrame frame = new MutableAudioFrame();

	HeraAudioProvider(final AudioPlayer player) {
		// Allocate a ByteBuffer for Discord4J's AudioProvider to hold audio data for Discord
		super(ByteBuffer.allocate(StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize()));
		// Set LavaPlayer's MutableAudioFrame to use the same buffer as the one we just allocated
		frame.setBuffer(getBuffer());
		this.player = player;
	}

	@Override
	public boolean provide() {
		// AudioPlayer writes audio data to its AudioFrame
		final boolean didProvide = player.provide(frame);
		// If audio was provided, flip from write-mode to read-mode
		if (didProvide) {
			getBuffer().flip();
		}
		return didProvide;
	}
}
