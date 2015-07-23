package codiodes.com.angelreader.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;
import codiodes.com.angelreader.R;

/**
 * Created by root on 15/6/15.
 */
public class MiscHelper {

    public static void stylizeActionBar(AppCompatActivity activity) {
        ActionBar bar = activity.getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.dark_green)));
        bar.setElevation(0);
        bar.setDisplayShowHomeEnabled(true);
        bar.setIcon(R.mipmap.ic_launcher);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static SweetAlertDialog getProgressDialog(Activity activity, String title) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog.setCancelable(false);
        return  sweetAlertDialog;
    }

    public static MaterialDialog getWarningDialog(Context context, int title, int subtitle) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(subtitle)
                .cancelable(true)
                .build();
        return dialog;
    }

    public static MaterialDialog getNetworkErrorDialog(Context context) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.internet_not_available_title)
                .content(R.string.internet_not_available_message)
                .autoDismiss(false)
                .cancelable(false)
                .build();
        return dialog;
    }

    public static SweetAlertDialog getNoInternetAccessErrorDialog(final Activity activity, String title) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setConfirmText(activity.getString(R.string.dialog_ok));
        return sweetAlertDialog;
    }
}
