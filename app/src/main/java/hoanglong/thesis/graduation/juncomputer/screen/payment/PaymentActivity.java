package hoanglong.thesis.graduation.juncomputer.screen.payment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUser;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmAddress;
import hoanglong.thesis.graduation.juncomputer.listener.UpdateStep;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseActivity;
import hoanglong.thesis.graduation.juncomputer.screen.payment.adapter.AddressAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.payment.listener.OnListenerPayment;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;

public class PaymentActivity extends BaseActivity implements View.OnClickListener,
        UpdateStep, OnListenerPayment, AddressAdapter.OnClickAddressListener {

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
    @BindView(R.id.recycler_address)
    RecyclerView mRecyclerAddress;
    private List<AddressUser> mAddressUsers;
    private AddressUser mAddressChoose;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
        mRelativeAddNewLocation.setOnClickListener(this);
        mRelativeContinue.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAddressUsers = new ArrayList<>();
        updateAddress();
        mTextTitle.setText(getString(R.string.title_address_order));
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
                if (mAddressChoose != null) {
                    mRelativeContinue.setClickable(true);
                    mRelativeContinue.setBackgroundResource(R.drawable.custom_button1);
                    mRelativeStep2.setBackgroundResource(R.drawable.circle_2);
                    mTextTitle.setText(getString(R.string.title_payment));
                    FragmentTransactionUtils.addFragment(getSupportFragmentManager(),
                            PaymentFragment.newInstance(mAddressChoose),
                            R.id.frame_step,
                            NewAddressFragment.TAG, true, -1, -1);
                } else {
                    mRelativeContinue.setClickable(false);
                    mRelativeContinue.setBackgroundResource(R.drawable.custom_button4);
                }
                break;
        }
    }

    @Override
    public void updateStep() {
        mRelativeStep2.setBackgroundResource(R.drawable.circle_off);
        mRelativeStep3.setBackgroundResource(R.drawable.circle_off);
        mTextTitle.setText(getString(R.string.title_address_order));
    }

    @Override
    public void updateStep3() {
        mRelativeStep3.setBackgroundResource(R.drawable.circle_2);
        mTextTitle.setText(getString(R.string.title_confirm));
    }

    @Override
    public void backStep3() {
        mTextTitle.setText(getString(R.string.title_payment));
        mRelativeStep3.setBackgroundResource(R.drawable.circle_off);
    }

    @Override
    public void onUpdateAddress() {
        updateAddress();
    }

    @Override
    public void updateAddress() {
        mAddressUsers.clear();
        mAddressUsers.addAll(RealmAddress.getListAddress());
        if (mAddressChoose == null) {
            mRelativeContinue.setClickable(false);
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button4);
        } else {
            mRelativeContinue.setClickable(true);
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button1);
        }
        AddressAdapter addressAdapter = new AddressAdapter(mAddressUsers, this);
        mRecyclerAddress.setAdapter(addressAdapter);
    }

    @Override
    public void chooseAddress(AddressUser addressUser) {
        mAddressChoose = addressUser;
        if (mAddressChoose == null) {
            mRelativeContinue.setClickable(false);
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button4);
        } else {
            mRelativeContinue.setClickable(true);
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button1);
        }
    }

    @Override
    public void onClickUpdateAddress(AddressUser addressUser) {
        FragmentTransactionUtils.addFragment(getSupportFragmentManager(),
                NewAddressFragment.newInstance(addressUser),
                R.id.frame_add_address,
                NewAddressFragment.TAG, true, -1, -1);
    }
}
