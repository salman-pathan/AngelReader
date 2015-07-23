package codiodes.com.angelreader.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import codiodes.com.angelreader.constant.InternetAvailability;
import codiodes.com.angelreader.constant.ServiceConstant;

public class InternetService extends IntentService {

    public static final String URL = "http://www.google.com";
    public static final int DELAY_MILLIS = 5000;
    public static final int TIMEOUT = 8;
    Intent responseIntent;
    OkHttpClient client;

    public InternetService() {
        super("InternetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        client = new OkHttpClient();
//        broadcastNetworkAvailability();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        client = new OkHttpClient();
        client.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
        broadcastNetworkAvailability();
        return super.onStartCommand(intent, flags, startId);
    }

    public void broadcastNetworkAvailability() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                makeRequest();
                Log.i("PING", "PING");
                handler.postDelayed(this, DELAY_MILLIS);
            }
        };
        handler.postDelayed(runnable, DELAY_MILLIS);
    }

    private void makeRequest() {
        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                responseIntent = new Intent(ServiceConstant.BROADCAST_ACTION)
                        .putExtra(ServiceConstant.INTERNET_AVAILABILITY_STATUS,
                                InternetAvailability.NOT_AVAILABLE);
                LocalBroadcastManager.getInstance(InternetService.this).sendBroadcast(responseIntent);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                responseIntent = new Intent(ServiceConstant.BROADCAST_ACTION)
                        .putExtra(ServiceConstant.INTERNET_AVAILABILITY_STATUS, InternetAvailability.AVAILABLE);
                LocalBroadcastManager.getInstance(InternetService.this).sendBroadcast(responseIntent);
            }
        });
    }
}
