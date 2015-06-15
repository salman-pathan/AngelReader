package codiodes.com.angelreader.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import codiodes.com.angelreader.R;
import codiodes.com.angelreader.fragment.FeedListFragment;
import codiodes.com.angelreader.fragment.StartScreenFragment;
import codiodes.com.angelreader.helper.FragmentHelper;


public class MainActivity extends ActionBarActivity implements StartScreenFragment.SuccessStartListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Add Start Screen Fragment
        FragmentHelper.addFragment(R.id.container, new StartScreenFragment(), this);
    }

    @Override
    public void success() {
        FragmentHelper.replaceFragment(R.id.container, new FeedListFragment(), this);
    }
}
