package hera.propertyHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.constants.BotConstants;
import hera.enums.BotSettings;
import hera.enums.BoundChannel;
import hera.enums.YoutubeSettings;
import sx.blah.discord.handle.obj.IGuild;

public class ProjectInitalizer {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProjectInitalizer.class);
	
	public ProjectInitalizer() {
	}
	
	public void initalizeProperties() {
		LOG.debug("Start of: ProjectInitializer.initializeProperties");
		PropertiesHandler propHandler = new PropertiesHandler(BotConstants.SETTINGS_PROPERTY_LOCATION);
		propHandler.load();
		for (BotSettings value : BotSettings.values()) {
			if (propHandler.containsKey(value.getPropertyName())) {
				value.setPropertyValue(propHandler.getProperty(value.getPropertyName()));
			}
		}
		LOG.debug("End of: ProjectInitializer.initializeProperties");
	}
	
	public void initalizeBindings(IGuild iGuild) {
		LOG.debug("Start of: ProjectInitializer.initalizeBindings");
		PropertiesHandler propHandler = new PropertiesHandler(BotConstants.BINDING_PROPERTY_LOCATION);
		propHandler.load();
		for (BoundChannel value : BoundChannel.values()) {
			value.setBoundChannel(propHandler.containsKey(value.getPropertyName()) ? iGuild.getChannelByID(new Long(propHandler.getProperty(value.getPropertyName()))) : value.getBoundChannel());
		}
		LOG.debug("End of: ProjectInitializer.initalizeBindings");
	}
	
	public void initializeYoutubeProperties() {
		LOG.debug("Start of: ProjectInitializer.initializeYoutubeProperties");
		PropertiesHandler propHandler = new PropertiesHandler(BotConstants.YOUTUBE_PROPERTY_LOCATION);
		propHandler.load();
		for (YoutubeSettings value : YoutubeSettings.values()) {
			if(propHandler.containsKey(value.getPropertyName())) {
				value.setPropertyValue(propHandler.getProperty(value.getPropertyName()));
			}
		}
		LOG.debug("End of: ProjectInitializer.initializeYoutubeProperties");
	}
}