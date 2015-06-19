package codiodes.com.angelreader.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import codiodes.com.angelreader.R;
import codiodes.com.angelreader.helper.MiscHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartScreenFragment extends Fragment {

    SuccessStartListener listener;

    public StartScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_screen, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (SuccessStartListener) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MiscHelper.isOnline(getActivity())) {
            listener.success();
        } else {
            //TODO: Add fancy alert dialog and make it modular.
            new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.internet_not_available_title)
                    .setMessage(R.string.internet_not_available_message)
                    .show();
        }
    }

    public interface SuccessStartListener {
        public void success();
    }
}
