package d4jbot.misc;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class SingletonInstancer {
	private static AudioPlayerManager apmInstance;
	
	public static AudioPlayerManager getAPMInstance() {
		if (apmInstance == null) {
			apmInstance = new DefaultAudioPlayerManager();
		}
		return apmInstance;
	}
}
