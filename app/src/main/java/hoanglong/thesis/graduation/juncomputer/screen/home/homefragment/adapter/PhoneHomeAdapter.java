package hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
import hoanglong.thesis.graduation.juncomputer.data.model.home.Km;

public class PhoneHomeAdapter extends RecyclerView.Adapter<PhoneHomeAdapter.PhoneViewHolder> {

    private List<Km> mPhones;
    private LayoutInflater mInflater;

    public PhoneHomeAdapter(List<Km> phones) {
        mPhones = phones;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mInflater.inflate(R.layout.item_phone_home, viewGroup, false);
        return new PhoneViewHolder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder phoneViewHolder, int i) {
        Km phone = mPhones.get(i);
        phoneViewHolder.bindData(phone);
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

        void bindData(Km phone) {
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
                String temp = phone.getPromo().replaceAll("\\n", "")
                        .replaceAll(" {10}", "");
                mTextPromo.setText(temp);
            }
            if (!phone.getDiscount().equals("")) {
                mTextSale.setText(phone.getDiscount());
            } else if (!phone.getInstallment().equals("")) {
                mTextSale.setText(phone.getInstallment());
            } else if (!phone.getShockprice().equals("")) {
                mTextSale.setText(phone.getShockprice());
            } else if (!phone.getNewItem().equals("")) {
                mTextSale.setText(phone.getNewItem());
                mRelativeSale.setBackgroundColor(Color.parseColor("#3971c4"));
            }
            mTextPhone.setText(phone.getTitleItem());
            mTextPricePhone.setText(phone.getNewPrice());
        }
    }
}
