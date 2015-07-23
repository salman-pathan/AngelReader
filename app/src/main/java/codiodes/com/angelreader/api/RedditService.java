package codiodes.com.angelreader.api;

import codiodes.com.angelreader.entity.Feed;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Salman Khan on 16/6/15.
 */
public interface RedditService {

    @GET("/r/{subreddit}/.json")
    void getFeeds(@Path("subreddit") String subReddit, Callback<Feed> feedCallback);

    @GET("/r/{subreddit}/.json")
    void getSubRedditNextFeed(@Path("subreddit") String subReddit, @Query("limit") int limit, @Query("after") String after, @Query("count") int count, Callback<Feed> feedCallback);

    @GET("/r/{subreddit}/.json")
    void getSubRedditPreviousFeed(@Path("subreddit") String subReddit, @Query("limit") int limit, @Query("before") String before, @Query("count") int count, Callback<Feed> feedCallback);
}
