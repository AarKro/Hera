package d4jbot.enums;

import d4jbot.constants.BotConstants;
import d4jbot.misc.PropertiesHandler;
import sx.blah.discord.handle.obj.IChannel;

public enum BoundChannel {
	REPORT("reportChannel", null), MUSIC("musicChannel", null);
	
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
			PropertiesHandler propertiesHandler = new PropertiesHandler(BotConstants.BINDING_PROPERTY_LOCATION);
			propertiesHandler.load();
			propertiesHandler.put(propertyName, "" + boundChannel.getLongID());
			propertiesHandler.save("binding saved");
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	
}