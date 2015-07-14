package codiodes.com.angelreader.api;

import codiodes.com.angelreader.entity.Feed;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by Salman Khan on 19/6/15.
 */
public class SubRedditReaderService {

    RedditService service;
    String subReddit;

    public SubRedditReaderService(String endPoint, String subReddit) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endPoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        service = restAdapter.create(RedditService.class);
        this.subReddit = subReddit;
    }

    public void getFeed(String subReddit, Callback<Feed> callback) {
        service.getFeeds(subReddit, callback);
    }

    public void loadUpdate(String subReddit, int limit, String after, Callback<Feed> callback) {
        service.getSubRedditFeed(subReddit, limit, after, callback);
    }

}
