package codiodes.com.angelreader.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import codiodes.com.angelreader.R;

/**
 * Created by root on 15/6/15.
 */
public class MiscHelper {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static SweetAlertDialog getProgressDialog(Activity activity, String title) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog.setCancelable(false);
        return  sweetAlertDialog;
    }
}
