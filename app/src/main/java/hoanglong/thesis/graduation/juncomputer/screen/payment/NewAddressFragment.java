package hoanglong.thesis.graduation.juncomputer.screen.payment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hoanglong.thesis.graduation.juncomputer.R;

public class NewAddressFragment extends Fragment {

    public static final String TAG = NewAddressFragment.class.getName();


    public NewAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_address, container, false);
    }

}
