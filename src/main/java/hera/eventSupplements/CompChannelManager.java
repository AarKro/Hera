package hera.eventSupplements;

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
	private List<IVoiceChannel> channelsToDelete;
	
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
						LOG.error("Start of CompChannelManager.run");
						
						channels = g.getVoiceChannels();
						compChannels = new ArrayList<IVoiceChannel>();
						channelsToDelete = new ArrayList<IVoiceChannel>();
						
						LOG.error("CompChannelManager.run is collecting Comptryhard Channels");
						for(IVoiceChannel vc : channels) {
							if(vc.getName().startsWith("Comptryhard")) {
								compChannels.add(vc);
							}
						}
						
						LOG.error("CompChannelManager.run is checking for empty Comptryhard Channels");
						for(IVoiceChannel vc : compChannels) {
							if(vc.getConnectedUsers().isEmpty()) {
								channelsToDelete.add(vc);
							}
						}
						
						LOG.error("CompChannelManager.run is deleting empty Comptryhard Channels");
						for(IVoiceChannel vc : channelsToDelete) {
							vc.delete();
						}
						
						Thread.sleep(3600000);
						LOG.error("End of CompChannelManager.run");
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
