package hera.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.constants.BotConstants;

public class PropertiesHandler extends Properties {
	
	private static final Logger LOG = LoggerFactory.getLogger(PropertiesHandler.class);
	
	private File propertyFile;
	
	public PropertiesHandler(String propertyName) {
		File baseLocation = new File(BotConstants.PROPERTIES_BASE_LOCATION);
		if (!baseLocation.isDirectory()) {
			baseLocation.mkdirs();
		}
		String propertyLocation = BotConstants.PROPERTIES_BASE_LOCATION + propertyName;
		propertyFile = new File(propertyLocation);

		try {
			if (!propertyFile.exists()) {
				propertyFile.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean save(String comments) {
		LOG.debug("Start of: PropertiesHandler.save");
		try {
			this.store(new FileOutputStream(propertyFile), comments);
			return true;
		} catch (IOException e) {
			LOG.error("IOException while trying to save property file");
			LOG.error(e.getMessage() + " : " + e.getCause());
			return false;
		} finally {
			LOG.debug("End of: PropertiesHandler.save");
		}
	}
	
	public boolean load() {
		LOG.debug("Start of: PropertiesHandler.load");
		try {
			this.load(new FileInputStream(propertyFile));
			return true;
		} catch (IOException e) {
			LOG.error("IOException while trying to load property file");
			LOG.error(e.getMessage() + " : " + e.getCause());
			return false;
		} finally {
			LOG.debug("End of: PropertiesHandler.load");
		}
	}
}
