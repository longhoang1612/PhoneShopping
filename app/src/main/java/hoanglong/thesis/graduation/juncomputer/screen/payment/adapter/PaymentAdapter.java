package hoanglong.thesis.graduation.juncomputer.screen.payment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.DeliveryViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<String> mPayments;
    private OnClickDeliveryListener mOnClickDeliveryListener;
    private int mLastSelectedPosition = -1;

    public PaymentAdapter(List<String> delivery, OnClickDeliveryListener onClickDeliveryListener) {
        mPayments = delivery;
        mOnClickDeliveryListener = onClickDeliveryListener;
    }

    @NonNull
    @Override
    public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_payment, viewGroup, false);
        return new DeliveryViewHolder(view, mOnClickDeliveryListener, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryViewHolder deliveryViewHolder, int i) {
        String delivery = mPayments.get(i);
        deliveryViewHolder.bindData(delivery, i);
    }

    @Override
    public int getItemCount() {
        return mPayments != null ? mPayments.size() : 0;
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
                    mOnClickDeliveryListener.choosePayment(mDelivery);
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
        void choosePayment(String payment);
    }
}
