package d4jbot.enums;

import d4jbot.constants.BotConstants;
import d4jbot.misc.PropertiesHandler;

public enum YoutubeSettings {
	APPLICATION_NAME("applicationName", ""), API_KEY("APIKey", ""), NUMBER_OF_VIDEOS_RETURNED("numberOfVideosReturned", "");
	
	private String propertyName;
	private String propertyValue;

	private YoutubeSettings(String propertyName, String propertyValue) {
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
			PropertiesHandler propHandler = new PropertiesHandler(BotConstants.YOUTUBE_PROPERTY_LOCATION);
			propHandler.load();
			propHandler.put(propertyName, propertyValue);	
			propHandler.save("setting saved");
		}
	}
}
