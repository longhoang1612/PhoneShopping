package hoanglong.thesis.graduation.juncomputer.screen.cart.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import hoanglong.thesis.graduation.juncomputer.utils.UploadCart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> mCartItems;
    private LayoutInflater mInflater;
    private OnUpdatePrice mOnUpdatePrice;

    public CartAdapter(List<CartItem> cartItems, OnUpdatePrice onUpdatePrice) {
        mCartItems = cartItems;
        mOnUpdatePrice = onUpdatePrice;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mInflater.inflate(R.layout.item_cart, viewGroup, false);
        return new CartViewHolder(view, viewGroup.getContext(), mOnUpdatePrice);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder cartViewHolder, int i) {
        CartItem cartItem = mCartItems.get(i);
        cartViewHolder.bindData(cartItem);
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    public interface OnUpdatePrice {
        void onUpdatePrice(List<CartItem> cartItems);

        void updateCart(List<CartItem> cartItems);
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext;
        @BindView(R.id.image_item_cart)
        ImageView mImageItemCart;
        @BindView(R.id.text_name_item)
        TextView mTextNameItem;
        @BindView(R.id.text_price_item)
        TextView mTextPriceItem;
        @BindView(R.id.ic_close)
        ImageView mImageCloseItem;
        @BindView(R.id.relative_add)
        RelativeLayout mRelativeAdd;
        @BindView(R.id.relative_minus)
        RelativeLayout mRelativeMinus;
        @BindView(R.id.text_quantity)
        TextView mTextQuantity;
        private int number;
        private CartItem mCartItem;
        private OnUpdatePrice mOnUpdatePrice;
        private boolean mIsLogin;

        CartViewHolder(@NonNull View itemView, Context context, OnUpdatePrice onUpdatePrice) {
            super(itemView);
            mContext = context;
            mOnUpdatePrice = onUpdatePrice;
            mIsLogin = SharedPrefs.getInstance().get(Constant.Login.LOGIN_STATUS, Boolean.class);
            ButterKnife.bind(this, itemView);
            mImageCloseItem.setOnClickListener(this);
            mRelativeAdd.setOnClickListener(this);
            mRelativeMinus.setOnClickListener(this);
        }

        public void bindData(CartItem cartItem) {
            if (cartItem == null) {
                return;
            }
            mCartItem = cartItem;
            Glide.with(mContext).load(cartItem.getImage()).into(mImageItemCart);
            mTextNameItem.setText(cartItem.getName());
            mTextPriceItem.setText(cartItem.getPrice());
            mTextQuantity.setText(String.valueOf(cartItem.getQuantity()));
            number = cartItem.getQuantity();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.relative_add:
                    number += 1;
                    mTextQuantity.setText(String.valueOf(number));
                    RealmCart.addToCart(mCartItem);
                    mOnUpdatePrice.onUpdatePrice(mCartItems);
                    if (mIsLogin) {
                        UploadCart.uploadCart();
                    }
                    break;
                case R.id.relative_minus:
                    if (number != 0) {
                        number--;
                        mTextQuantity.setText(String.valueOf(number));
                        RealmCart.removeInCart(mCartItem);
                        mOnUpdatePrice.onUpdatePrice(mCartItems);
                    } else {
                        RealmCart.deleteItemFromCart(mCartItem);
                        mOnUpdatePrice.updateCart(mCartItems);
                    }
                    if (mIsLogin) {
                        UploadCart.uploadCart();
                    }
                    break;
                case R.id.ic_close:
                    alert();
                    break;
            }
        }

        private void alert() {
            AlertDialog.Builder b = new AlertDialog.Builder(mContext);
            b.setTitle("Thông báo");
            b.setMessage("Bạn muốn xóa sản phẩm này khỏi giỏ hàng?");
            b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RealmCart.deleteItemFromCart(mCartItem);
                    mOnUpdatePrice.updateCart(mCartItems);
                }
            });
            b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            b.create().show();
        }
    }
}
