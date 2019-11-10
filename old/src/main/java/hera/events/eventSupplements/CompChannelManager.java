package hera.events.eventSupplements;

import java.util.ArrayList;
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
	private List<IVoiceChannel> compChannels;
	
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
							
							for(IVoiceChannel vc : compChannels) {
								LOG.error("CompChannelManager.run is checking for empty Comptryhard Channels");
								if(vc.getConnectedUsers().isEmpty()) {
									LOG.error("CompChannelManager.run is deleting the empty Comptryhard Channels");
									vc.delete();
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
		
		compChannels = new ArrayList<>();
		channels = new ArrayList<>();
	}
	
	public void setGuild(IGuild g) {
		this.g = g;
		loadChannels();
	}
	
	private void loadChannels() {
		channels.addAll(g.getVoiceChannels());
		
		for(IVoiceChannel vc : channels) {
			LOG.error("CompChannelManager.run is collecting Comptryhard Channels");
			if(vc.getName().startsWith("Comptryhard")) {
				compChannels.add(vc);
			}
		}
		
		channels.clear();
	}
}
