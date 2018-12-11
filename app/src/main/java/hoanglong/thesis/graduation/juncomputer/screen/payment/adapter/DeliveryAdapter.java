package hoanglong.thesis.graduation.juncomputer.screen.payment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<String> mDeliveries;
    private OnClickDeliveryListener mOnClickDeliveryListener;
    private int mLastSelectedPosition = -1;

    public DeliveryAdapter(List<String> delivery, OnClickDeliveryListener onClickDeliveryListener) {
        mDeliveries = delivery;
        mOnClickDeliveryListener = onClickDeliveryListener;
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_delivery, viewGroup, false);
        return new DeliveryViewHolder(view, mOnClickDeliveryListener, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder deliveryViewHolder, int i) {
        String delivery = mDeliveries.get(i);
        deliveryViewHolder.bindData(delivery, i);
    }

    @Override
    public int getItemCount() {
        return mDeliveries != null ? mDeliveries.size() : 0;
    }

    class DeliveryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnClickDeliveryListener mOnClickDeliveryListener;
        private String mDelivery;
        @BindView(R.id.radio_type)
        RadioButton mRadioType;
        @BindView(R.id.text_type)
        TextView mTextType;
        private Context mContext;
        private int mPosition;

        DeliveryViewHolder(@NonNull View itemView, OnClickDeliveryListener onClickDeliveryListener, Context context) {
            super(itemView);
            mOnClickDeliveryListener = onClickDeliveryListener;
            mContext = context;
            ButterKnife.bind(this, itemView);
            mRadioType.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.radio_type:
                    mOnClickDeliveryListener.chooseDelivery(mDelivery);
                    mLastSelectedPosition = mPosition;
                    notifyDataSetChanged();
                    break;
            }
        }

        public void bindData(String delivery, int position) {
            if (delivery == null) {
                return;
            }
            mPosition = position;
            mDelivery = delivery;
            mTextType.setText(delivery);
            mRadioType.setChecked(mLastSelectedPosition == position);
        }

    }

    public interface OnClickDeliveryListener {
        void chooseDelivery(String delivery);
    }
}
