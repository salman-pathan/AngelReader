package codiodes.com.angelreader.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import codiodes.com.angelreader.R;
import codiodes.com.angelreader.entity.Children;
import codiodes.com.angelreader.entity.Feed;

/**
 * Created by Salman Khan on 16/6/15.
 */
public class FeedListAdapter extends BaseAdapter{

    Activity activity;
    LayoutInflater layoutInflater;
    List<Children> childrens;

    public FeedListAdapter(Activity activity, LayoutInflater layoutInflater, Feed feed) {
        this.activity = activity;
        this.layoutInflater = layoutInflater;
        this.childrens = feed.getData().getChildren();
    }

    public void add(List<Children> childrens) {
        this.childrens.addAll(childrens);
    }

    @Override
    public int getCount() {
        return childrens.size();
    }

    @Override
    public Object getItem(int position) {
        return childrens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.single_feed_layout, null);
        }

        Children children = childrens.get(position);

        //  Creating view objects.
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.feed_thumbnail);
        TextView feedTitle = (TextView) convertView.findViewById(R.id.feed_title);
        TextView authorName = (TextView) convertView.findViewById(R.id.author_name);
        TextView ups = (TextView) convertView.findViewById(R.id.ups_count);
        TextView comments = (TextView) convertView.findViewById(R.id.comments);

        //  Getting images asynchronously.
        Picasso.with(activity)
                .load(children.getData().getUrl())
                .placeholder(R.drawable.reddit)
                .error(R.drawable.reddit)
                .into(thumbnail);

        feedTitle.setText(children.getData().getTitle());
        authorName.setText(children.getData().getAuthor());
        ups.setText("" + children.getData().getUps());
        comments.setText("" + children.getData().getNum_comments());

        return convertView;
    }
}
