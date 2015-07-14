package codiodes.com.angelreader.service;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Salman Khan on 14/7/15.
 */
public class InternetStatusHandler {

    NetworkStatusTask task = new NetworkStatusTask(new Handler());
    Timer timer = new Timer();

    class NetworkStatusTask extends TimerTask {
        Handler handler;

        public NetworkStatusTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
}
