package codiodes.com.angelreader.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import codiodes.com.angelreader.R;
import codiodes.com.angelreader.helper.MiscHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartScreenFragment extends Fragment {

    public static final int DELAY = 3000;
    SuccessStartListener listener;

    @Override
    public void onResume() {
        super.onResume();
        if (MiscHelper.isOnline(getActivity())) {
            simulateLoading();
        } else {
            String errorTitle = getActivity().getResources().getString(R.string.internet_not_available_title);
            SweetAlertDialog sweetAlertDialog = MiscHelper.getNoInternetAccessErrorDialog(getActivity(), errorTitle);
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    getActivity().finish();
                }
            });
            sweetAlertDialog.show();
        }
    }

    public StartScreenFragment() {
        // Required empty public constructor
    }

    private void simulateLoading() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                listener.success();
            }
        }, DELAY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_screen, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (SuccessStartListener) activity;
    }

    public interface SuccessStartListener {
        void success();
    }
}
