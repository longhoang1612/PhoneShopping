package hoanglong.thesis.graduation.juncomputer.screen.cart;

import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseActivity;
import hoanglong.thesis.graduation.juncomputer.screen.cart.adapter.CartAdapter;

public class CartActivity extends BaseActivity implements CartAdapter.OnUpdatePrice {

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
    private List<CartItem> mCartItems;
    private int total;


    @Override
    protected int getLayoutResources() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
       // mCartItems = new ArrayList<>();
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

        mTextTotalCart.setText(String.valueOf(total) + "₫");
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
}
