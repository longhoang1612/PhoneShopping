package hoanglong.thesis.graduation.juncomputer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.listener.UpdateStep;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, UpdateStep {

    @BindView(R.id.relative_add_new_location)
    RelativeLayout mRelativeAddNewLocation;
    @BindView(R.id.relative_continue)
    RelativeLayout mRelativeContinue;
    @BindView(R.id.text_title_fragment)
    TextView mTextTitle;
    @BindView(R.id.relative_step2)
    RelativeLayout mRelativeStep2;
    @BindView(R.id.relative_step3)
    RelativeLayout mRelativeStep3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        mRelativeAddNewLocation.setOnClickListener(this);
        mRelativeContinue.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTextTitle.setText("Địa chỉ nhận hàng");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_add_new_location:
                FragmentTransactionUtils.addFragment(getSupportFragmentManager(),
                        new NewAddressFragment(),
                        R.id.frame_add_address,
                        NewAddressFragment.TAG, true, -1, -1);
                break;
            case R.id.relative_continue:
                mRelativeStep2.setBackgroundResource(R.drawable.circle_2);
                mTextTitle.setText("Thanh toán");
                FragmentTransactionUtils.addFragment(getSupportFragmentManager(),
                        new PaymentFragment(),
                        R.id.frame_step,
                        NewAddressFragment.TAG, true, -1, -1);
                break;
        }
    }

    @Override
    public void updateStep() {
        mRelativeStep2.setBackgroundResource(R.drawable.circle_off);
        mRelativeStep3.setBackgroundResource(R.drawable.circle_off);
        mTextTitle.setText("Địa chỉ nhận hàng");
    }

    @Override
    public void updateStep3() {
        mRelativeStep3.setBackgroundResource(R.drawable.circle_2);
        mTextTitle.setText("Xác nhận");
    }

    @Override
    public void backStep3() {
        mTextTitle.setText("Thanh toán");
        mRelativeStep3.setBackgroundResource(R.drawable.circle_off);
    }
}
