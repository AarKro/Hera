package hera.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.constants.BotConstants;
import hera.misc.PropertiesHandler;

public enum DriveSettings {
	APPLICATION_NAME("applicationName", ""), API_KEY("APIKey", "");
	
	private static final Logger LOG = LoggerFactory.getLogger(DriveSettings.class);
	
	private String propertyName;
	private String propertyValue;

	private DriveSettings(String propertyName, String propertyValue) {
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
		LOG.debug("Start of: DriveSettings.setPropertyValue");
		this.propertyValue = propertyValue;
		if (propertyValue != null) {
			LOG.info("Drive settings attribute " + propertyName + " has not been set yet, thus it will be set now with value " + propertyValue);
			PropertiesHandler propHandler = new PropertiesHandler(BotConstants.YOUTUBE_PROPERTY_LOCATION);
			propHandler.load();
			propHandler.put(propertyName, propertyValue);	
			propHandler.save("setting saved");
			LOG.info("Drive settings successfully modified");
		}
		LOG.debug("End of: DriveSettings.setPropertyValue");
	}
}
