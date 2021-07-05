package hera.core.api.handlers;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import hera.database.entities.Token;
import hera.database.types.TokenKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static hera.store.DataStore.STORE;

public class YouTubeApiHandler {
	private static final Logger LOG = LoggerFactory.getLogger(YouTubeApiHandler.class);

	private static YouTube youtube;

	private static String apiToken;

	private static String applicationName;

	public static void initialise() {
		List<Token> appNames = STORE.tokens().forKey(TokenKey.YOUTUBE_API_APP_NAME);
		List<Token> apiTokens = STORE.tokens().forKey(TokenKey.YOUTUBE_API_TOKEN);

		if (appNames.isEmpty() || apiTokens.isEmpty()) {
			LOG.error("Credentials for youtube API not found");
			youtube = null;
			apiToken = null;
			applicationName = null;
			return;
		}

		applicationName = appNames.get(0).getToken();
		apiToken = apiTokens.get(0).getToken();

		youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
		}).setApplicationName(applicationName).build();
	}

	public static String getYoutubeVideoFromKeyword(String keyword) {
		try {
			YouTube.Search.List search = youtube.search().list("id,snippet");
			search.setKey(apiToken);
			search.setQ(keyword);
			search.setType("video");
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
			search.setMaxResults((long) 1);

			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();

			if(searchResultList != null) {
				for(SearchResult result : searchResultList) {
					return "https://www.youtube.com/watch?v=" + result.getId().getVideoId();
				}
			}

		} catch (IOException e) {
			LOG.error("Error while calling the YouTube API");
			LOG.error(e.getMessage() + " : " + e.getCause());
		}

		return null;
	}
}
