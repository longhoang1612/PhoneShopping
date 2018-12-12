package hoanglong.thesis.graduation.juncomputer.screen.manageOrder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;

public class ManagerOrderFragment extends BaseFragment {

    public static final String TAG = ManagerOrderFragment.class.getName();

    public static ManagerOrderFragment newInstance() {

        Bundle args = new Bundle();

        ManagerOrderFragment fragment = new ManagerOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_manager_order;
    }

    @Override
    protected void initComponent(View view) {

    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }
}
