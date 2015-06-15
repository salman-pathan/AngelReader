package codiodes.com.angelreader.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by root on 15/6/15.
 */
public class FragmentHelper {

    public static void addFragment(int containerId, Fragment fragment, ActionBarActivity activity) {
        try {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.add(containerId, fragment);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void replaceFragment(int containerId, Fragment fragment, ActionBarActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.commit();
        manager.popBackStack();
    }
}
