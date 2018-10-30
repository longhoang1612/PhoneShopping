package hoanglong.thesis.graduation.juncomputer.Category.subCategory.phone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.model.ItemPhoneProduct;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ItemPhoneViewHolder> {

    private List<ItemPhoneProduct> mPhoneProducts;
    private LayoutInflater mLayoutInflater;
    private OnClickProductListener mOnClickProductListener;

    public PhoneAdapter(List<ItemPhoneProduct> phoneProducts, OnClickProductListener onClickProductListener) {
        mPhoneProducts = phoneProducts;
        mOnClickProductListener = onClickProductListener;
    }

    @NonNull
    @Override
    public ItemPhoneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_product, viewGroup, false);
        return new ItemPhoneViewHolder(view, viewGroup.getContext(), mOnClickProductListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPhoneViewHolder itemPhoneViewHolder, int i) {
        ItemPhoneProduct itemPhoneProduct = mPhoneProducts.get(i);
        itemPhoneViewHolder.bindData(itemPhoneProduct);
    }

    @Override
    public int getItemCount() {
        return mPhoneProducts != null ? mPhoneProducts.size() : 0;
    }

    public static class ItemPhoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext;
        private OnClickProductListener mListener;
        private ImageView mImageView;
        private TextView mTextSale;
        private TextView mTextPrice;
        private TextView mTextProduct;
        private TextView mTextInfo;
        private TextView mTextNumberRatting;
        private RatingBar mRatingBar;
        private ItemPhoneProduct mItemPhoneProduct;

        public ItemPhoneViewHolder(@NonNull View itemView, Context context, OnClickProductListener listener) {
            super(itemView);
            mContext = context;
            mListener = listener;
            mImageView = itemView.findViewById(R.id.image_product);
            mTextSale = itemView.findViewById(R.id.text_sale);
            mTextPrice = itemView.findViewById(R.id.text_price);
            mTextProduct = itemView.findViewById(R.id.text_name_product);
            mTextInfo = itemView.findViewById(R.id.text_info_product);
            mTextNumberRatting = itemView.findViewById(R.id.text_number_rating);
            mRatingBar = itemView.findViewById(R.id.rating_bar);
            itemView.setOnClickListener(this);
        }

        public void bindData(ItemPhoneProduct itemPhoneProduct) {
            if (itemPhoneProduct == null) {
                return;
            }
            mItemPhoneProduct = itemPhoneProduct;
            Glide.with(mContext).load(itemPhoneProduct.getImage()).into(mImageView);
            Log.d("ABCD", "bindData: " + "https://"+itemPhoneProduct.getImage());
            mTextSale.setText(itemPhoneProduct.getDeal());
            mTextPrice.setText(itemPhoneProduct.getPrice());
//            mTextInfo.setText(itemPhoneProduct.getInfo());
            mTextProduct.setText(itemPhoneProduct.getName());
            mTextNumberRatting.setText(itemPhoneProduct.getNumberRating());
            mRatingBar.setRating(Float.valueOf(itemPhoneProduct.getRating()));
        }

        @Override
        public void onClick(View view) {
            mListener.onClickItemProduct(mItemPhoneProduct);
        }
    }

    public interface OnClickProductListener {
        void onClickItemProduct(ItemPhoneProduct itemPhoneProduct);
    }
}
