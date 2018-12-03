package hoanglong.thesis.graduation.juncomputer;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.listener.UpdateStep;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;

public class PaymentFragment extends BaseFragment implements View.OnClickListener {


    public static final String TAG = PaymentFragment.class.getName();
    private UpdateStep mUpdateStep;
    @BindView(R.id.relative_continue)
    RelativeLayout mRelativeContinue;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_continue:
                if (getFragmentManager() != null) {
                    mUpdateStep.updateStep3();
                    FragmentTransactionUtils.addFragment(getFragmentManager(),
                            new ConfirmFragment(),
                            R.id.frame_step,
                            ConfirmFragment.TAG, true, -1, -1);
                }
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUpdateStep = (UpdateStep) context;
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_payment;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mRelativeContinue.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mUpdateStep.updateStep();
    }
}
