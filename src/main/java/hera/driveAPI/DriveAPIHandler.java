package hera.driveAPI;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import hera.enums.DriveSettings;

public class DriveAPIHandler {

	private static final Logger LOG = LoggerFactory.getLogger(DriveAPIHandler.class);
	
	public static DriveAPIHandler instance;
	
	public static DriveAPIHandler getInstance() {
		if(instance == null) instance = new DriveAPIHandler();
		return instance;
	}
	
	private Drive drive;
	
	private DriveAPIHandler() {
		drive = new Drive.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
		    public void initialize(HttpRequest request) throws IOException {
		    }
		}).setApplicationName(DriveSettings.APPLICATION_NAME.getPropertyValue()).build();
	}
	
	public void storeFileInCloud() {
		File body = new File();
		body.setDescription("Log file from " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
		body.setMimeType("application/vnd.google-apps.file");
	}
}
