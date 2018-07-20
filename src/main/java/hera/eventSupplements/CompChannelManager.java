package hera.eventSupplements;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class CompChannelManager {
	private static final Logger LOG = LoggerFactory.getLogger(CompChannelManager.class);
	
	private static CompChannelManager instance;	
	private IGuild g;
	
	private List<IVoiceChannel> channels;
	
	public static CompChannelManager getInstance() {
		if (instance == null) {
			instance = new CompChannelManager();
		}
		return instance;
	}
	
	public CompChannelManager() { 	
		Runnable runnable = new Runnable() {

			public void run() {
				while(true) {
					try {
						if(g != null) {
							LOG.error("Start of CompChannelManager.run");
							
							// Loads all Channels from scratch, due to the possibility of it being a different Guild
							channels = g.getVoiceChannels();
							
							// CompChannels are searched by name not ID, since if I would save the ID's of the created Channels
							// It would create null pointers once the Guild has been switched
							for(IVoiceChannel vc : channels) {
								LOG.error("CompChannelManager.run is collecting Comptryhard Channels");
								if(vc.getName().startsWith("Comptryhard")) {
									LOG.error("CompChannelManager.run is checking for empty Comptryhard Channels");
									if(vc.getConnectedUsers().isEmpty()) {
										LOG.error("CompChannelManager.run is deleting the empty Comptryhard Channels");
										vc.delete();
									}
								}
							}
							
							LOG.error("End of CompChannelManager.run");
						}
						Thread.sleep(3600000);
					} catch (Exception e) {
						LOG.error("Exception in CompChannelManager.Thread");
						LOG.error(e.getMessage() + " : " + e.getCause());
					}
				}
			}
			
		};

		Thread thread = new Thread(runnable);
		LOG.info("CompChannelManager.Thread created");
		thread.start();
		LOG.info("CompChannelManager.Thread started");
	}
	
	public void setGuild(IGuild g) {
		this.g = g;
	}
}
