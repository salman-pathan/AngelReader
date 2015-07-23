package codiodes.com.angelreader.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Salman Khan on 14/7/15.
 */
public class InternetStatusHandler implements Runnable{

    Handler handler;
    Context context;

    public InternetStatusHandler(Context context) {
        handler = new Handler();
        this.context = context;
    }

    @Override
    public void run() {
        handler.postDelayed(this, 5000);
        //  Start background intent service
        Intent internetServiceIntent = new Intent(context, InternetService.class);
        context.startService(internetServiceIntent);
        Log.e("POST HANDLE", "PING!");
    }
}
