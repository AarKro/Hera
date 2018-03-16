package d4jbot.youtubeAPI;

import java.io.IOException;
import java.util.List;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import d4jbot.enums.YoutubeSettings;

public class YoutubeAPIHandler {

	private YouTube youtube;
		
	public static YoutubeAPIHandler instance;
	
	public static YoutubeAPIHandler getInstance() {
		if(instance == null) instance = new YoutubeAPIHandler();
		return instance;
	}
	
	// default constructor
	private YoutubeAPIHandler() { 
		youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
		    public void initialize(HttpRequest request) throws IOException {
		    }
		}).setApplicationName(YoutubeSettings.APPLICATION_NAME.getPropertyValue()).build();
	}
	
	public String getYoutubeVideoFromKeyword(String keyword) {
		try {
			
			YouTube.Search.List search = youtube.search().list("id,snippet");
			search.setKey(YoutubeSettings.API_KEY.getPropertyValue());
			search.setQ(keyword);
			search.setType("video");
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
			search.setMaxResults(Long.parseLong(YoutubeSettings.NUMBER_OF_VIDEOS_RETURNED.getPropertyValue()));
			
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			
			if(searchResultList != null) {
				for(SearchResult result : searchResultList) {
					return "https://www.youtube.com/watch?v=" + result.getId().getVideoId();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}