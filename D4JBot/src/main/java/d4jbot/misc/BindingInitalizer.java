package d4jbot.misc;

import java.util.List;
import java.util.function.Predicate;

import d4jbot.constants.BotConstants;
import d4jbot.enums.BoundChannel;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

public class BindingInitalizer {
	public BindingInitalizer(IGuild iGuild) {
		PropertiesHandler propHandler = new PropertiesHandler(BotConstants.BINDING_PROPERTY_LOCATION);
		propHandler.load();
		for (BoundChannel value : BoundChannel.values()) {
			value.setBoundChannel(propHandler.containsKey(value.getPropertyName()) ? iGuild.getChannelByID(new Long(propHandler.getProperty(value.getPropertyName()))) : value.getBoundChannel());
		}
	}
	
	
}
