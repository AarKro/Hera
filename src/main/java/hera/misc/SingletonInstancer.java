package hera.misc;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;

public class SingletonInstancer {
	
	private static AudioPlayerManager apmInstance;
	
	public static AudioPlayerManager getAPMInstance() {
		if (apmInstance == null) {
			apmInstance = new DefaultAudioPlayerManager();
		}
		return apmInstance;
	}
}
