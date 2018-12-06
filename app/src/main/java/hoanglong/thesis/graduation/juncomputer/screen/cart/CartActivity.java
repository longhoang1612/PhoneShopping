package hoanglong.thesis.graduation.juncomputer.screen.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseActivity;
import hoanglong.thesis.graduation.juncomputer.screen.cart.adapter.CartAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.HomeActivity;
import hoanglong.thesis.graduation.juncomputer.screen.payment.PaymentActivity;

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
    private List<CartItem> mCartItems;
    private int total;


    @Override
    protected int getLayoutResources() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
        mImageBack.setOnClickListener(this);
        mRelativePayment.setOnClickListener(this);
        mRelativeGoShopping.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (RealmCart.getCartOffline() == null || RealmCart.getCartOffline().size() == 0) {
            mGroup.setVisibility(View.VISIBLE);
            mGroupCart.setVisibility(View.GONE);
        } else {
            mGroup.setVisibility(View.GONE);
            mGroupCart.setVisibility(View.VISIBLE);

            setUpCart();
            onUpdatePrice();
        }
    }

    private void setUpCart() {
        mCartItems = new ArrayList<>();
        mCartItems = RealmCart.getCartOffline();
        CartAdapter cartAdapter = new CartAdapter(mCartItems, this);
        mRecyclerCart.setAdapter(cartAdapter);
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
        mTextTotalCart.setText(fmt.format(total));
    }

    @Override
    public void updateCart() {
        if (RealmCart.getCartOffline() == null || RealmCart.getCartOffline().size() == 0) {
            mGroup.setVisibility(View.VISIBLE);
            mGroupCart.setVisibility(View.GONE);
        } else {
            mGroup.setVisibility(View.GONE);
            mGroupCart.setVisibility(View.VISIBLE);
            setUpCart();
            onUpdatePrice();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back:
                onBackPressed();
                break;
            case R.id.relative_payment:
                openPayment();
                break;
            case R.id.relative_go_shopping:
                Intent intent = new Intent(CartActivity.this,HomeActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void openPayment() {
        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
        startActivity(intent);
    }
}
