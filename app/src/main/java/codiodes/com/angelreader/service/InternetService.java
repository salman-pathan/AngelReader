package codiodes.com.angelreader.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import codiodes.com.angelreader.constant.InternetAvailability;
import codiodes.com.angelreader.constant.ServiceConstant;

public class InternetService extends IntentService {

    public static final String URL = "http://www.google.com";
    Intent responseIntent;
    OkHttpClient client;

    public InternetService() {
        super("InternetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        client = new OkHttpClient();
        broadcastNetworkAvailability();
    }

    public void broadcastNetworkAvailability() {
        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                responseIntent = new Intent(ServiceConstant.BROADCAST_ACTION)
                        .putExtra(ServiceConstant.INTERNET_AVAILABILITY_STATUS, InternetAvailability.NOT_AVAILABLE);
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
