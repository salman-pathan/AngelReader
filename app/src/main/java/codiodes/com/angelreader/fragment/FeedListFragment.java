package codiodes.com.angelreader.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import codiodes.com.angelreader.R;
import codiodes.com.angelreader.adapter.FeedListAdapter;
import codiodes.com.angelreader.api.SubRedditReaderService;
import codiodes.com.angelreader.constant.InternetAvailability;
import codiodes.com.angelreader.constant.ServiceConstant;
import codiodes.com.angelreader.entity.Children;
import codiodes.com.angelreader.entity.Feed;
import codiodes.com.angelreader.helper.MiscHelper;
import codiodes.com.angelreader.observer.NetworkObserver;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedListFragment extends Fragment implements NetworkObserver {

    public static final String ENDPOINT = "http://www.reddit.com";
    public static final int FEED_LIMIT = 25;
    public static final String FEED_PARCEL_KEY = "FeedParcelKey";
    FeedListAdapter feedListAdapter;
    Feed feed;
    List<Children> childrens;

    @InjectView(R.id.list_refresh_layout)
    SwipyRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.feed_list)
    ListView feedList;

    SubRedditReaderService subRedditReaderService;

    public FeedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        updateNetworkStatus();
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            feed = savedInstanceState.getParcelable(FEED_PARCEL_KEY);
        } else {
            setupService();

            final SweetAlertDialog progressDialog = MiscHelper.
                    getProgressDialog(getActivity(), getResources().getString(R.string.loading));
            progressDialog.show();
            getFeed(progressDialog);

            feedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("On Feed Click", "Feed Clicked");
                    Children children = childrens.get(position);
                    String url = children.getData().getUrl();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });
        }
    }

    private void getFeed(final SweetAlertDialog alertDialog) {
        subRedditReaderService.getFeed("programming", new Callback<Feed>() {
            @Override
            public void success(Feed feed, Response response) {
                FeedListFragment.this.feed = feed;
                Log.i("AFTER_LINK_INITIAL", feed.getData().getAfter());
                Log.i("AFTER_LINK_MAIN", FeedListFragment.this.feed.getData().getAfter());
                childrens = feed.getData().getChildren();
                feedListAdapter = new FeedListAdapter(getActivity(), getActivity().getLayoutInflater(), feed);
                feedList.setAdapter(feedListAdapter);
                alertDialog.dismissWithAnimation();
                Log.e("SUCCESS", feed.getKind());
            }

            @Override
            public void failure(RetrofitError error) {
                alertDialog.dismissWithAnimation();
                Log.e("FEED ERROR", error.getMessage());
            }
        });
    }

    private void setupService() {
        subRedditReaderService = new SubRedditReaderService(ENDPOINT, "programming");
        swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {

                switch (swipyRefreshLayoutDirection) {
                    case TOP:
                        loadPreviousFeed();
                        break;

                    case BOTTOM:
                        loadNextFeed();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void loadPreviousFeed() {
        String beforeLink = feed.getData().getBefore();
        if (beforeLink != null) {
            subRedditReaderService.loadPreviousFeed("programming", FEED_LIMIT, beforeLink, FEED_LIMIT,new Callback<Feed>() {
                @Override
                public void success(Feed feed, Response response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (feed.getData().getChildren().size() > 0) {
                        updateLinks(feed);
                        updateListAdapter(feed);
                    } else {
                        //  Shows warning message if no new result is found.
                        MaterialDialog alertDialog = MiscHelper.getWarningDialog(getActivity(), R.string.no_result_found, R.string.oops);
                        alertDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("FEED ERROR", error.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            MaterialDialog dialog = MiscHelper.getWarningDialog(getActivity(), R.string.nothing_to_load_title, R.string.nothing_to_load_sub_title);
            dialog.show();
        }
    }

    private void updateLinks(Feed feed) {
        this.feed.getData().setAfter(feed.getData().getAfter());
        this.feed.getData().setBefore(feed.getData().getBefore());
    }


    /**
     *  Loads new feed when users pull the screen from bottom.
     */
    private void loadNextFeed() {
        String afterLink = feed.getData().getAfter();
        if (afterLink != null) {
            subRedditReaderService.loadNextFeed("programming", FEED_LIMIT, afterLink, FEED_LIMIT, new Callback<Feed>() {
                @Override
                public void success(Feed feed, Response response) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (feed.getData().getChildren().size() > 0) {
                        //  Updates the after link.
                        Log.i("AFTER_LINK_ON_SCROLL", feed.getData().getAfter());
                        updateLinks(feed);
                        updateListAdapter(feed);
                    } else {
                        //  Shows warning message if no new result is found.
                        MaterialDialog alertDialog = MiscHelper.getWarningDialog(getActivity(), R.string.no_result_found, R.string.oops);
                        alertDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("FEED ERROR", error.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            MaterialDialog dialog = MiscHelper.getWarningDialog(getActivity(), R.string.nothing_to_load_title, R.string.nothing_to_load_sub_title);
            dialog.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(FEED_PARCEL_KEY, feed);
    }

    @Override
    public void updateNetworkStatus() {
        //  Receive intent from background service
        IntentFilter intentFilter = new IntentFilter(ServiceConstant.BROADCAST_ACTION);
        final MaterialDialog dialog = MiscHelper.getNetworkErrorDialog(getActivity());
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                InternetAvailability networkStatus = (InternetAvailability) intent.getSerializableExtra(ServiceConstant.INTERNET_AVAILABILITY_STATUS);

                switch (networkStatus) {
                    case AVAILABLE:
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        break;
                    case NOT_AVAILABLE:
                        if (!dialog.isShowing()) {
                            dialog.show();
                        }
                        break;
                }
                Log.i("BROADCAST", networkStatus.toString());
            }
        }, intentFilter);
    }

    private void updateListAdapter(Feed feed) {
        feedListAdapter = new FeedListAdapter(getActivity(), getActivity().getLayoutInflater(), feed);
        feedListAdapter.update(feed.getData().getChildren());
        feedList.setAdapter(feedListAdapter);
    }
}
