package d4jbot.enums;

import d4jbot.constants.BotConstants;
import d4jbot.misc.PropertiesHandler;

public enum BotSettings {
	BOT_PREFIX("prefix", "$"), BOT_VERSION("version", "v0.4.4"), BOT_DEV_STATUS("devStatus", "devl"), BOT_VOLUME("volume", "100");
	
	private String propertyName;
	private String propertyValue;

	private BotSettings(String propertyName, String propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
		if (propertyValue != null) {
			PropertiesHandler propHandler = new PropertiesHandler(BotConstants.SETTINGS_PROPERTY_LOCATION);
			propHandler.load();
			propHandler.put(propertyName, propertyValue);	
			propHandler.save("setting saved");
		}
	}
	
	
}
