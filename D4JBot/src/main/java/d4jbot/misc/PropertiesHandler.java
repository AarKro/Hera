package d4jbot.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import d4jbot.constants.BotConstants;

public class PropertiesHandler extends Properties {
	File propertyFile;
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
		try {
			this.store(new FileOutputStream(propertyFile), comments);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}
	
	public boolean load() {
		try {
			this.load(new FileInputStream(propertyFile));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
}
