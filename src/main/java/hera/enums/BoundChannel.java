package hera.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.constants.BotConstants;
import hera.misc.PropertiesHandler;
import sx.blah.discord.handle.obj.IChannel;

public enum BoundChannel {
	REPORT("reportChannel", null), MUSIC("musicChannel", null), ANNOUNCEMENTS("announcements", null);
	
	private static final Logger LOG = LoggerFactory.getLogger(BoundChannel.class);
	
	private IChannel boundChannel;
	private String propertyName;
	
	PropertiesHandler propertiesHandler;
	
	// constructor
	private BoundChannel(String name, IChannel channel) {
		boundChannel = channel;
		propertyName = name;
	}

	// getters & setters
	public IChannel getBoundChannel() {
		return boundChannel;
	}

	public void setBoundChannel(IChannel boundChannel) {
		this.boundChannel = boundChannel;
		if (boundChannel != null) {
			LOG.info("Channel binding " + propertyName + " has not been set yet, thus it will be set now with value " + boundChannel.getLongID() );
			PropertiesHandler propertiesHandler = new PropertiesHandler(BotConstants.BINDING_PROPERTY_LOCATION);
			propertiesHandler.load();
			propertiesHandler.put(propertyName, "" + boundChannel.getLongID());
			propertiesHandler.save("binding saved");
			LOG.info("Property file successfully modified");
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	
}