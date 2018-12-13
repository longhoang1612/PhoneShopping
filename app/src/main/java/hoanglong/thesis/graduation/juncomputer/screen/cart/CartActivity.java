package hoanglong.thesis.graduation.juncomputer.screen.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseActivity;
import hoanglong.thesis.graduation.juncomputer.screen.cart.adapter.CartAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.HomeActivity;
import hoanglong.thesis.graduation.juncomputer.screen.login.LoginActivity;
import hoanglong.thesis.graduation.juncomputer.screen.payment.PaymentActivity;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity implements CartAdapter.OnUpdatePrice, View.OnClickListener {

    @BindView(R.id.group)
    Group mGroup;
    @BindView(R.id.group_cart)
    Group mGroupCart;
    @BindView(R.id.recycler_cart)
    RecyclerView mRecyclerCart;
    @BindView(R.id.text_total_cart)
    TextView mTextTotalCart;
    @BindView(R.id.relative_payment)
    RelativeLayout mRelativePayment;
    @BindView(R.id.relative_go_shopping)
    RelativeLayout mRelativeGoShopping;
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    @BindView(R.id.progress_cart)
    ProgressBar mProgressCart;
    private boolean mIsLogin;
    private User mUser;
    private List<CartItem> mCartItems;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
        mProgressCart.setVisibility(View.VISIBLE);
        mGroup.setVisibility(View.GONE);
        mGroupCart.setVisibility(View.GONE);
        mImageBack.setOnClickListener(this);
        mRelativePayment.setOnClickListener(this);
        mRelativeGoShopping.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mIsLogin = SharedPrefs.getInstance().get(Constant.Login.LOGIN_STATUS, Boolean.class);
        List<CartItem> cartItemsRealm;
        mCartItems = new ArrayList<>();

        cartItemsRealm = RealmCart.getCartOffline();
        if (cartItemsRealm == null || cartItemsRealm.size() == 0) {
            if (mIsLogin) {
                Gson gson = new Gson();
                String json = SharedPrefs.getInstance().get(Constant.Login.OBJECT_USER_LOGIN, String.class);
                mUser = gson.fromJson(json, User.class);
                if (mUser == null) {
                    return;
                }
                mGroup.setVisibility(View.GONE);
                mGroupCart.setVisibility(View.VISIBLE);
                getUserInfo();
            } else {
                mGroup.setVisibility(View.VISIBLE);
                mGroupCart.setVisibility(View.GONE);
            }
        } else {
            mGroup.setVisibility(View.GONE);
            mGroupCart.setVisibility(View.VISIBLE);
            setUpCart(cartItemsRealm);
            onUpdatePrice(cartItemsRealm);
        }
    }

    private void setUpCart(List<CartItem> cartItemList) {
        CartAdapter cartAdapter = new CartAdapter(cartItemList, this);
        mRecyclerCart.setAdapter(cartAdapter);
    }

    @Override
    public void onUpdatePrice(List<CartItem> cartItems) {
        int total = 0;
        for (CartItem cartItem : cartItems
                ) {
            String a = cartItem.getPrice().split("₫")[0];
            String b = a.replaceAll("\\.", "");
            int quantity = cartItem.getQuantity();
            int price = Integer.parseInt(b);
            total += price * quantity;
        }

        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        mTextTotalCart.setText(fmt.format(total));
    }

    @Override
    public void updateCart(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.size() == 0) {
            mGroup.setVisibility(View.VISIBLE);
            mGroupCart.setVisibility(View.GONE);
        } else {
            mGroup.setVisibility(View.GONE);
            mGroupCart.setVisibility(View.VISIBLE);
            setUpCart(cartItems);
            onUpdatePrice(cartItems);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back:
                onBackPressed();
                break;
            case R.id.relative_payment:
                if (mIsLogin) {
                    openPayment();
                } else {
                    Intent intent = new Intent(CartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.relative_go_shopping:
                Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void openPayment() {
        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
        startActivity(intent);
    }

    public void getUserInfo() {
        Call<User> call = BaseService.getService().getProfileUser(mUser.getEmail());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.body() != null) {
                    mProgressCart.setVisibility(View.GONE);
                    mCartItems = response.body().getCartItems();
                    if (mCartItems == null || mCartItems.size() == 0) {
                        mGroup.setVisibility(View.VISIBLE);
                        mGroupCart.setVisibility(View.GONE);
                    } else {
                        mGroup.setVisibility(View.GONE);
                        mGroupCart.setVisibility(View.VISIBLE);
                        setUpCart(mCartItems);
                        onUpdatePrice(mCartItems);
                    }
                    if (RealmCart.getCartOffline().size() == 0) {
                        for (int i = 0; i < mCartItems.size(); i++) {
                            RealmCart.addToCart(mCartItems.get(i));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

            }
        });
    }
}
