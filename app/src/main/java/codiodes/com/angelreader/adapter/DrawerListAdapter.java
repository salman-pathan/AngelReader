package codiodes.com.angelreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import codiodes.com.angelreader.R;
import codiodes.com.angelreader.entity.NavigationItem;

/**
 * Created by Salman Khan on 11/7/15.
 */
public class DrawerListAdapter extends BaseAdapter {

    Context context;
    ArrayList<NavigationItem> navigationItems;

    public DrawerListAdapter(Context context, ArrayList<NavigationItem> navigationItems) {
        this.context = context;
        this.navigationItems = navigationItems;
    }

    @Override
    public int getCount() {
        return navigationItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.drawer_item, null);
        } else {
            view = convertView;
        }
        ImageView menuIcon = (ImageView) view.findViewById(R.id.menu_icon);
        TextView menuTitle = (TextView) view.findViewById(R.id.menu_title);
        TextView menuSubTitle = (TextView) view.findViewById(R.id.menu_subtitle);

        menuIcon.setBackgroundResource(navigationItems.get(position).getImageId());
        menuTitle.setText(navigationItems.get(position).getTitle());
        menuSubTitle.setText(navigationItems.get(position).getSubTitle());

        return view;
    }
}
