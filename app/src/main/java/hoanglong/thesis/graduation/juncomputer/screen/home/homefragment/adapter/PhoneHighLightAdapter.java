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
import hoanglong.thesis.graduation.juncomputer.data.model.home.Phone;

public class PhoneHighLightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Phone> mPhones;
    private LayoutInflater mInflater;

    public PhoneHighLightAdapter(List<Phone> phones) {
        mPhones = phones;
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
        if (position == 0 || position == 4) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Phone phone = mPhones.get(i);
        switch (viewHolder.getItemViewType()) {
            case 0:
                TopViewHolder topViewHolder = (TopViewHolder) viewHolder;
                topViewHolder.bindData(phone);
                break;
            case 1:
                PhoneViewHolder phoneViewHolder = (PhoneViewHolder) viewHolder;
                phoneViewHolder.bindData(phone);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mPhones != null ? mPhones.size() : 0;
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

        void bindData(Phone phone) {
            if (phone == null) {
                return;
            }
            Glide.with(mContext).load(phone.getImage()).into(mImagePhone);
            if (phone.getImagePromo() == null) {
                mImagePromo.setVisibility(View.GONE);
            } else {
                mImagePromo.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(phone.getImagePromo()).into(mImagePromo);
            }
            if (phone.getPromo() != null) {
                String temp = phone.getPromo().replaceAll("\\n","")
                        .replaceAll(" {10}","");
                mTextPromo.setText(temp);
            }
            if (!phone.getDiscount().equals("")) {
                mTextSale.setText(phone.getDiscount());
            } else if (!phone.getInstallment().equals("")) {
                mTextSale.setText(phone.getInstallment());
            } else if (!phone.getShockprice().equals("")) {
                mTextSale.setText(phone.getShockprice());
            }
            mTextPhone.setText(phone.getTitle());
            mTextPricePhone.setText(phone.getPrice());
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

        void bindData(Phone phone) {
            if (phone == null) {
                return;
            }
            Glide.with(mContext).load(phone.getImage()).into(mImagePhone);
            mTextPhone.setText(phone.getTitle());
            mTextPricePhone.setText(phone.getPrice());
            mTextSale.setText(phone.getInstallment());
        }
    }
}
