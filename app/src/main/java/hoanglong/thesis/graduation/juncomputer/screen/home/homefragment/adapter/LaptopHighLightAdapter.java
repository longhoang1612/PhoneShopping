package hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import hoanglong.thesis.graduation.juncomputer.data.model.home.Laptop;
import hoanglong.thesis.graduation.juncomputer.data.model.home.Phone;

public class LaptopHighLightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Laptop> mLaptops;
    private LayoutInflater mInflater;
    private OnClickLaptopListener mLaptopListener;

    public LaptopHighLightAdapter(List<Laptop> laptops, OnClickLaptopListener onClickLaptopListener) {
        mLaptops = laptops;
        mLaptopListener = onClickLaptopListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        if (viewType == 0) {
            View viewTop = mInflater.inflate(R.layout.item_phone_top, viewGroup, false);
            return new TopViewHolder(viewTop, viewGroup.getContext(), mLaptopListener);
        } else {
            View view = mInflater.inflate(R.layout.item_phone_hl_home, viewGroup, false);
            return new PhoneViewHolder(view, viewGroup.getContext(), mLaptopListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Laptop laptop = mLaptops.get(i);
        switch (viewHolder.getItemViewType()) {
            case 0:
                TopViewHolder topViewHolder = (TopViewHolder) viewHolder;
                topViewHolder.bindData(laptop);
                break;
            case 1:
                PhoneViewHolder phoneViewHolder = (PhoneViewHolder) viewHolder;
                phoneViewHolder.bindData(laptop);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mLaptops != null ? mLaptops.size() : 0;
    }

    static class PhoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_phone_home)
        ImageView mImagePhone;
        @BindView(R.id.text_phone_home)
        TextView mTextPhone;
        @BindView(R.id.price_phone_home)
        TextView mTextPricePhone;
        @BindView(R.id.image_promo)
        ImageView mImagePromo;
        @BindView(R.id.text_promo_phone_home)
        TextView mTextPromo;
        @BindView(R.id.text_sale)
        TextView mTextSale;
        @BindView(R.id.relative_sale)
        RelativeLayout mRelativeSale;
        private Context mContext;
        private OnClickLaptopListener mOnClickLaptopListener;
        private Laptop mLaptop;

        PhoneViewHolder(@NonNull View itemView, Context context, OnClickLaptopListener onClickLaptopListener) {
            super(itemView);
            mContext = context;
            mOnClickLaptopListener = onClickLaptopListener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        void bindData(Laptop laptop) {
            if (laptop == null) {
                return;
            }
            mLaptop = laptop;
            Glide.with(mContext).load(laptop.getImage()).into(mImagePhone);
            if (laptop.getImagePromo() == null) {
                mImagePromo.setVisibility(View.GONE);
            } else {
                mImagePromo.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(laptop.getImagePromo()).into(mImagePromo);
            }
            if (laptop.getPromo() != null) {
                String temp = laptop.getPromo().replaceAll("\\n", "")
                        .replaceAll(" {10}", "");
                mTextPromo.setText(temp);
            }
            if (!laptop.getInstallment().equals("")) {
                mTextSale.setText(laptop.getInstallment());
            }
            mTextPhone.setText(laptop.getTitle());
            mTextPricePhone.setText(laptop.getPrice());
        }

        @Override
        public void onClick(View v) {
            mOnClickLaptopListener.onClickLaptop(mLaptop);
        }
    }

    static class TopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image_phone_home)
        ImageView mImagePhone;
        @BindView(R.id.text_phone_home)
        TextView mTextPhone;
        @BindView(R.id.price_phone_home)
        TextView mTextPricePhone;
        @BindView(R.id.text_sale)
        TextView mTextSale;
        private Context mContext;
        private Laptop mLaptop;
        private OnClickLaptopListener mLaptopListener;

        TopViewHolder(@NonNull View itemView, Context context, OnClickLaptopListener onClickLaptopListener) {
            super(itemView);
            mContext = context;
            mLaptopListener = onClickLaptopListener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bindData(Laptop laptop) {
            if (laptop == null) {
                return;
            }
            mLaptop = laptop;
            Glide.with(mContext).load(laptop.getImage()).into(mImagePhone);
            mTextPhone.setText(laptop.getTitle());
            mTextPricePhone.setText(laptop.getPrice());
            mTextSale.setText(laptop.getInstallment());
        }

        @Override
        public void onClick(View v) {
            mLaptopListener.onClickLaptop(mLaptop);
        }
    }

    public interface OnClickLaptopListener {
        void onClickLaptop(Laptop laptop);
    }
}
