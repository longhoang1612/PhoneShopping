package hoanglong.thesis.graduation.juncomputer.screen.payment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUser;
import hoanglong.thesis.graduation.juncomputer.listener.UpdateStep;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.payment.adapter.DeliveryAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.payment.adapter.PaymentAdapter;
import hoanglong.thesis.graduation.juncomputer.utils.FormatDate;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;

import static hoanglong.thesis.graduation.juncomputer.screen.payment.NewAddressFragment.BUNDLE_ADDRESS;

public class PaymentFragment extends BaseFragment implements View.OnClickListener,
        DeliveryAdapter.OnClickDeliveryListener, PaymentAdapter.OnClickDeliveryListener {

    public static final String TAG = PaymentFragment.class.getName();
    private UpdateStep mUpdateStep;
    @BindView(R.id.relative_continue)
    RelativeLayout mRelativeContinue;
    @BindView(R.id.recycler_delivery)
    RecyclerView mRecyclerDelivery;
    @BindView(R.id.recycler_payment)
    RecyclerView mRecyclerPayment;
    private AddressUser mAddressUser;
    List<String> mTypeDeliveries;
    List<String> mTypePayments;
    private String mDelivery;
    private String mPayment;

    public static PaymentFragment newInstance(AddressUser addressUser) {

        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_ADDRESS, addressUser);
        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mAddressUser = getArguments().getParcelable(BUNDLE_ADDRESS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_continue:
                if (getFragmentManager() != null) {
                    mUpdateStep.updateStep3();
                    Log.d(TAG, "onClick: " + mAddressUser + ":"+mPayment+":"+mDelivery);
                    FragmentTransactionUtils.addFragment(getFragmentManager(),
                            ConfirmFragment.newInstance(mAddressUser, mPayment, mDelivery),
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
        checkChoose();
        mRelativeContinue.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDelivery();
        initPayment();
    }

    private void initPayment() {
        String typePayment = "Thanh toán tiền mặt khi nhận hàng";
        mTypePayments = new ArrayList<>();
        mTypePayments.add(typePayment);
        PaymentAdapter paymentAdapter = new PaymentAdapter(mTypePayments, this);
        mRecyclerPayment.setAdapter(paymentAdapter);
    }

    private void initDelivery() {
        mTypeDeliveries = new ArrayList<>();
        String typeDelivery = "Giao hàng tiêu chuẩn ( dự kiến giao hàng vào " + FormatDate.nextDay(6) + ")";
        String type2hDelivery = "Giao hàng 2h ( dự kiến giao hàng vào " + FormatDate.nextHour(2) + " ngày " + FormatDate.getCurrentDate() + ")";
        mTypeDeliveries.add(typeDelivery);
        mTypeDeliveries.add(type2hDelivery);
        DeliveryAdapter deliveryAdapter = new DeliveryAdapter(mTypeDeliveries, this);
        mRecyclerDelivery.setAdapter(deliveryAdapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mUpdateStep.updateStep();
    }

    @Override
    public void chooseDelivery(String delivery) {
        mDelivery = delivery;
        Toast.makeText(getContext(), mDelivery, Toast.LENGTH_SHORT).show();
        checkChoose();
    }

    private void checkChoose() {
        if (mDelivery == null || mPayment == null) {
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button4);
            mRelativeContinue.setClickable(false);
        } else {
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button1);
            mRelativeContinue.setClickable(true);
        }
    }

    @Override
    public void choosePayment(String payment) {
        mPayment = payment;
        Toast.makeText(getContext(), mPayment, Toast.LENGTH_SHORT).show();
        checkChoose();
    }
}
