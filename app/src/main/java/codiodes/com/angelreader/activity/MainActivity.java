package codiodes.com.angelreader.activity;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import codiodes.com.angelreader.R;
import codiodes.com.angelreader.adapter.DrawerListAdapter;
import codiodes.com.angelreader.constant.InternetAvailability;
import codiodes.com.angelreader.constant.ServiceConstant;
import codiodes.com.angelreader.entity.NavigationItem;
import codiodes.com.angelreader.fragment.FeedListFragment;
import codiodes.com.angelreader.fragment.StartScreenFragment;
import codiodes.com.angelreader.helper.FragmentHelper;
import codiodes.com.angelreader.helper.MiscHelper;
import codiodes.com.angelreader.service.InternetService;
import codiodes.com.angelreader.service.InternetStatusHandler;

public class MainActivity extends AppCompatActivity implements StartScreenFragment.SuccessStartListener {

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @InjectView(R.id.menu_list)
    ListView menuList;

    ActionBarDrawerToggle drawerToggle;
    ArrayList<NavigationItem> navigationItems;
    SweetAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        alertDialog = MiscHelper.getNoInternetAccessErrorDialog(MainActivity.this, "No Internet Access");

        Intent internetServiceIntent = new Intent(this, InternetService.class);
        startService(internetServiceIntent);
        Log.e("POST HANDLE", "PING!");

        //  Recieve intent from background serivce
//        IntentFilter intentFilter = new IntentFilter(ServiceConstant.BROADCAST_ACTION);
//        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                InternetAvailability availability = (InternetAvailability) intent.getSerializableExtra(ServiceConstant.INTERNET_AVAILABILITY_STATUS);
//                if (availability == InternetAvailability.NOT_AVAILABLE) {
//                    Snackbar.make(findViewById(android.R.id.content), "Not Available", Snackbar.LENGTH_LONG).show();
//                }
//                Log.i("BROADCAST", availability.toString());
//            }
//        }, intentFilter);

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        thread.interrupt();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        //  Top search bar configuration
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();

        //  Toggles hamburger menu.
        drawerToggle.onOptionsItemSelected(item);

        switch (itemId) {
            case R.id.action_refresh:
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void success() {
        FragmentHelper.replaceFragment(R.id.container, new FeedListFragment(), this);
    }

}
