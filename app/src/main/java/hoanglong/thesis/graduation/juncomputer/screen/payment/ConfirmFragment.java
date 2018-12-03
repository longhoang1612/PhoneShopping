package hoanglong.thesis.graduation.juncomputer.screen.payment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.listener.UpdateStep;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmFragment extends BaseFragment {


    public static final String TAG = ConfirmFragment.class.getName();
    private UpdateStep mUpdateStep;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUpdateStep = (UpdateStep) context;
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_confirm;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mUpdateStep.backStep3();
    }
}
