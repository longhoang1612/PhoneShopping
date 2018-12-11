package hoanglong.thesis.graduation.juncomputer.screen.payment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUser;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.listener.UpdateStep;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.cart.adapter.CartAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.payment.adapter.CartConfirmAdapter;

import static hoanglong.thesis.graduation.juncomputer.screen.payment.NewAddressFragment.BUNDLE_ADDRESS;

public class ConfirmFragment extends BaseFragment implements CartConfirmAdapter.OnUpdatePrice {

    public static final String TAG = ConfirmFragment.class.getName();
    private static final String BUNDLE_PAYMENT = "BUNDLE_PAYMENT";
    private static final String BUNDLE_DELIVERY = "BUNDLE_DELIVERY";
    private UpdateStep mUpdateStep;
    private AddressUser mAddressUser;
    private String mPayment;
    private String mDelivery;

    @BindView(R.id.text_name_confirm)
    TextView mTextName;
    @BindView(R.id.text_phone_confirm)
    TextView mTextPhone;
    @BindView(R.id.text_address_confirm)
    TextView mTextAddress;
    @BindView(R.id.text_delivery)
    TextView mTextDelivery;
    @BindView(R.id.text_payment)
    TextView mTextPayment;
    @BindView(R.id.recycler_order_confirm)
    RecyclerView mRecyclerOrderConfirm;
    private List<CartItem> mCartItems;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUpdateStep = (UpdateStep) context;
    }

    public static ConfirmFragment newInstance(AddressUser addressUser, String payment, String delivery) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_ADDRESS, addressUser);
        bundle.putString(BUNDLE_PAYMENT, payment);
        bundle.putString(BUNDLE_DELIVERY, delivery);
        ConfirmFragment fragment = new ConfirmFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mAddressUser = getArguments().getParcelable(BUNDLE_ADDRESS);
        mPayment = getArguments().getString(BUNDLE_PAYMENT);
        mDelivery = getArguments().getString(BUNDLE_DELIVERY);
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
        initRecyclerView();
        if (mAddressUser == null || mDelivery == null || mPayment == null) {
            return;
        }
        mTextName.setText(mAddressUser.getUserNameOrder());
        mTextPhone.setText(mAddressUser.getPhoneNumber());
        mTextAddress.setText(mAddressUser.getAddressOrder());
        mTextDelivery.setText(mDelivery);
        mTextPayment.setText(mPayment);
    }

    private void initRecyclerView() {
        mCartItems = new ArrayList<>();
        mCartItems.addAll(RealmCart.getCartOffline());
        CartConfirmAdapter cartAdapter = new CartConfirmAdapter(mCartItems, this);
        mRecyclerOrderConfirm.setNestedScrollingEnabled(false);
        mRecyclerOrderConfirm.setAdapter(cartAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mUpdateStep.backStep3();
    }

    @Override
    public void onUpdatePrice() {

    }

    @Override
    public void updateCart() {

    }
}
