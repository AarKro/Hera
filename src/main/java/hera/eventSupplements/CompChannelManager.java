package hera.eventSupplements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompChannelManager {
	private static final Logger LOG = LoggerFactory.getLogger(CompChannelManager.class);
	
	private static CompChannelManager instance;
	
	public static CompChannelManager getInstance() {
		if (instance == null) instance = new CompChannelManager();
		return instance;
	}
	
	public CompChannelManager() { 
		
		Runnable runnable = new Runnable() {

			public void run() {
				while(true) {
					try {
						//TODO: impl what it should do (search for comptryhard channels and delete them if empty)
						
						
						Thread.sleep(3600000);
					} catch (Exception e) {
						LOG.error("Exception in MessageOfTheDayManager.Thread");
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
}
