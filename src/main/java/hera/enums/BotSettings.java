package hera.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.constants.BotConstants;
import hera.propertyHandling.PropertiesHandler;

public enum BotSettings {
	BOT_PREFIX("prefix", "$"), BOT_VERSION("version", "v0.4.4"), BOT_DEV_STATUS("devStatus", "devl"), BOT_VOLUME("volume", "100"), LAST_MOTD("lastMotd", "01.01.1990"), SHAME_TIME("shameTime", "30000");

	private static final Logger LOG = LoggerFactory.getLogger(BotSettings.class);
	
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
		LOG.debug("Start of: BotSettings.setPropertyValue");
		this.propertyValue = propertyValue;
		if (propertyValue != null) {
			LOG.info("Property " + propertyName + " has not been set yet, thus it will be set now with value " + propertyValue );
			PropertiesHandler propHandler = new PropertiesHandler(BotConstants.SETTINGS_PROPERTY_LOCATION);
			propHandler.load();
			propHandler.put(propertyName, propertyValue);	
			propHandler.save("setting saved");
			LOG.info("Property file successfully modified");
		}
		LOG.debug("End of: BotSettings.setPropertyValue");
	}
	
	
}
