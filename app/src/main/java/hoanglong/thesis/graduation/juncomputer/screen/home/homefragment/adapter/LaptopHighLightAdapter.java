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

    public LaptopHighLightAdapter(List<Laptop> laptops) {
        mLaptops = laptops;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        if (viewType == 0) {
            View viewTop = mInflater.inflate(R.layout.item_phone_top, viewGroup, false);
            return new TopViewHolder(viewTop, viewGroup.getContext());
        } else {
            View view = mInflater.inflate(R.layout.item_phone_hl_home, viewGroup, false);
            return new PhoneViewHolder(view, viewGroup.getContext());
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

    static class PhoneViewHolder extends RecyclerView.ViewHolder {

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

        PhoneViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;
            ButterKnife.bind(this, itemView);
        }

        void bindData(Laptop laptop) {
            if (laptop == null) {
                return;
            }
            Glide.with(mContext).load(laptop.getImage()).into(mImagePhone);
            if (laptop.getImagePromo() == null) {
                mImagePromo.setVisibility(View.GONE);
            } else {
                mImagePromo.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(laptop.getImagePromo()).into(mImagePromo);
            }
            if (laptop.getPromo() != null) {
                String temp = laptop.getPromo().replaceAll("\\n","")
                        .replaceAll(" {10}","");
                mTextPromo.setText(temp);
            }
            if (!laptop.getInstallment().equals("")) {
                mTextSale.setText(laptop.getInstallment());
            }
            mTextPhone.setText(laptop.getTitle());
            mTextPricePhone.setText(laptop.getPrice());
        }
    }

    static class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_phone_home)
        ImageView mImagePhone;
        @BindView(R.id.text_phone_home)
        TextView mTextPhone;
        @BindView(R.id.price_phone_home)
        TextView mTextPricePhone;
        @BindView(R.id.text_sale)
        TextView mTextSale;
        private Context mContext;

        TopViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            mContext = context;
            ButterKnife.bind(this, itemView);
        }

        void bindData(Laptop laptop) {
            if (laptop == null) {
                return;
            }
            Glide.with(mContext).load(laptop.getImage()).into(mImagePhone);
            mTextPhone.setText(laptop.getTitle());
            mTextPricePhone.setText(laptop.getPrice());
            mTextSale.setText(laptop.getInstallment());
        }
    }
}
