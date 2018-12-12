package hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment;

import android.os.Bundle;
import android.view.View;

import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;

public class SeenFragment extends BaseFragment {

    public static final String TAG = SeenFragment.class.getName();

    public static SeenFragment newInstance() {

        Bundle args = new Bundle();

        SeenFragment fragment = new SeenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_seen;
    }

    @Override
    protected void initComponent(View view) {

    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }
}
