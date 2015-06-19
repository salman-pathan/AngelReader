package codiodes.com.angelreader.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import codiodes.com.angelreader.R;
import codiodes.com.angelreader.adapter.FeedListAdapter;
import codiodes.com.angelreader.entity.Children;
import codiodes.com.angelreader.entity.Feed;
import codiodes.com.angelreader.helper.MiscHelper;
import codiodes.com.angelreader.service.RedditService;
import codiodes.com.angelreader.service.SubRedditReaderService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedListFragment extends Fragment {

    public static final String ENDPOINT = "http://www.reddit.com";
    public static final int FEED_LIMIT = 25;
    FeedListAdapter feedListAdapter;
    Feed feed;
    List<Children> childrens;

    @InjectView(R.id.list_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.feed_list)
    ListView feedList;

    SubRedditReaderService subRedditReaderService;

    public FeedListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        subRedditReaderService = new SubRedditReaderService(ENDPOINT, "programming");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                subRedditReaderService.loadUpdate("gaming", FEED_LIMIT, feed.getData().getAfter(), new Callback<Feed>() {
                    @Override
                    public void success(Feed feed, Response response) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (feed.getData().getChildren().size() > 0) {
//                            FeedListFragment.this.feed = feed;
                            feedListAdapter = new FeedListAdapter(getActivity(), getActivity().getLayoutInflater(), feed);
                            feedListAdapter.add(feed.getData().getChildren());
                            feedList.setAdapter(feedListAdapter);
                            feedListAdapter.notifyDataSetChanged();
                        } else {
                            SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                            alertDialog.setTitleText(getActivity().getResources().getString(R.string.no_result_found));
                            alertDialog.setCancelable(true);
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("FEED ERROR", error.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        final SweetAlertDialog alertDialog = MiscHelper.
                getProgressDialog(getActivity(), getResources().getString(R.string.loading));
        alertDialog.show();

        subRedditReaderService.getFeed("programming", new Callback<Feed>() {
            @Override
            public void success(Feed feed, Response response) {
                FeedListFragment.this.feed = feed;
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
