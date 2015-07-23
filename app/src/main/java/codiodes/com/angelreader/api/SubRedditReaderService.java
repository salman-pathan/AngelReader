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

    public void loadPreviousFeed(String subReddit, int limit, String before, int count, Callback<Feed> callback) {
        service.getSubRedditPreviousFeed(subReddit, limit, before, count, callback);
    }

    public void loadNextFeed(String subReddit, int limit, String after, int count, Callback<Feed> callback) {
        service.getSubRedditNextFeed(subReddit, limit, after, count, callback);
    }
}
