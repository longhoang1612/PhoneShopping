package hoanglong.thesis.graduation.juncomputer.screen.payment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.model.order.Order;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUser;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.listener.UpdateStep;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.payment.adapter.CartConfirmAdapter;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.FormatDate;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hoanglong.thesis.graduation.juncomputer.screen.payment.NewAddressFragment.BUNDLE_ADDRESS;

public class ConfirmFragment extends BaseFragment implements CartConfirmAdapter.OnUpdatePrice, View.OnClickListener {

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
    @BindView(R.id.text_confirm_price)
    TextView mTextConfirmPrice;
    @BindView(R.id.relative_confirm_order)
    RelativeLayout mRelativeConfirm;
    private List<CartItem> mCartItems;
    private int total;
    private ProgressDialog dialogProgress;
    private User mUser;

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
        mRelativeConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initRecyclerView();
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get(Constant.Login.OBJECT_USER_LOGIN, String.class);
        mUser = gson.fromJson(json, User.class);
        if (mUser == null) {
            return;
        }
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
        onUpdatePrice();
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
        total = 0;
        for (CartItem cartItem : RealmCart.getCartOffline()
                ) {
            String a = cartItem.getPrice().split("₫")[0];
            String b = a.replaceAll("\\.", "");
            total += Integer.parseInt(b) * cartItem.getQuantity();
        }

        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        mTextConfirmPrice.setText(fmt.format(total));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_confirm_order:
                uploadOrder();
                break;
        }
    }

    private void uploadOrder() {
        showProgress();

        String idUser = mUser.getId();
        String typeOrder = mDelivery;
        String dateOrder = FormatDate.getCurrentDateWithHour();
        String statusOrder = "Đang xác nhận";
        String nameUser = mAddressUser.getUserNameOrder();
        String phoneNumber = mAddressUser.getPhoneNumber();
        String typePayment = mPayment;
        String addressUser = mAddressUser.getAddressOrder();
        List<CartItem> cartItems = new ArrayList<>();

        for (int i = 0; i < mCartItems.size(); i++) {
            CartItem cartItem = mCartItems.get(i);
            CartItem cartUpload = new CartItem(cartItem.getName(), cartItem.getPrice(), cartItem.getQuantity(), cartItem.getImage());
            cartItems.add(cartUpload);
        }

        Order order = new Order(idUser, typeOrder, dateOrder, statusOrder, nameUser, phoneNumber, typePayment, cartItems, addressUser);

        Log.d(TAG, "uploadOrder: " + order.toString());

        Call<Order> callOrder = BaseService.getService().createOrder(order);
        callOrder.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                hideProgress();
                if (getContext() == null) return;
                Toasty.success(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT, true).show();
                RealmCart.deleteAll();
                if(getActivity()==null) return;
                getActivity().finish();
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                hideProgress();
                if (getContext() == null) return;
                Toasty.success(getContext(), "Đặt hàng thất bại", Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    public void showProgress() {
        if (dialogProgress != null) {
            return;
        }
        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Xác nhận đơn hàng...");
        dialogProgress.show();
    }

    public void hideProgress() {
        if (dialogProgress == null)
            return;
        dialogProgress.dismiss();
        dialogProgress = null;
    }
}
