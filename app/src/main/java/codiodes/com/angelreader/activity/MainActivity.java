package codiodes.com.angelreader.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import codiodes.com.angelreader.R;
import codiodes.com.angelreader.adapter.DrawerListAdapter;
import codiodes.com.angelreader.entity.NavigationItem;
import codiodes.com.angelreader.fragment.FeedListFragment;
import codiodes.com.angelreader.fragment.StartScreenFragment;
import codiodes.com.angelreader.helper.FragmentHelper;
import codiodes.com.angelreader.helper.MiscHelper;


public class MainActivity extends AppCompatActivity implements StartScreenFragment.SuccessStartListener {

    @InjectView(R.id.hamburger_menu)
    RelativeLayout hamburgerMenu;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @InjectView(R.id.menu_list)
    ListView menuList;

    ActionBarDrawerToggle drawerToggle;
    ArrayList<NavigationItem> navigationItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //  Stylize the action bar
        MiscHelper.stylizeActionBar(this);

        navigationItems = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawer();
        setMenusForHamburgerMenu();

        //  Add Start Screen Fragment
        FragmentHelper.addFragment(R.id.container, new StartScreenFragment(), this);
    }

    private void setMenusForHamburgerMenu() {
        try {
            //  Hard coded Strings
            String[] title = {"Home", "Settings", "About"};

            String subtitle = getResources().getString(R.string.placeholder_title);

            int settingMenuPosition = 0;
            int shareMenuPosition = 1;
            int helpMenuPosition = 2;

            navigationItems.add(new NavigationItem(R.drawable.ic_public_black_24dp, title[settingMenuPosition], subtitle));
            navigationItems.add(new NavigationItem(R.drawable.ic_public_black_24dp, title[shareMenuPosition], subtitle));
            navigationItems.add(new NavigationItem(R.drawable.ic_public_black_24dp, title[helpMenuPosition], subtitle));

            DrawerListAdapter listAdapter = new DrawerListAdapter(this, navigationItems);
            menuList.setAdapter(listAdapter);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    void setupDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void success() {
        FragmentHelper.replaceFragment(R.id.container, new FeedListFragment(), this);
    }

}
