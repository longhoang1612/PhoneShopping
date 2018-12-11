package hoanglong.thesis.graduation.juncomputer.screen.payment.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUser;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmAddress;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<AddressUser> mAddressUsers;
    private OnClickAddressListener mOnClickAddressListener;
    private int mLastSelectedPosition = -1;

    public AddressAdapter(List<AddressUser> addressUsers, OnClickAddressListener onClickAddressListener) {
        mAddressUsers = addressUsers;
        mOnClickAddressListener = onClickAddressListener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_address, viewGroup, false);
        return new AddressViewHolder(view, mOnClickAddressListener, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder addressViewHolder, int i) {
        AddressUser addressUser = mAddressUsers.get(i);
        addressViewHolder.bindData(addressUser, i);
    }

    @Override
    public int getItemCount() {
        return mAddressUsers != null ? mAddressUsers.size() : 0;
    }

    class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnClickAddressListener mOnClickAddressListener;
        private AddressUser mAddressUser;
        @BindView(R.id.text_name_user)
        TextView mTextViewNameUser;
        @BindView(R.id.text_address)
        TextView mTextAddress;
        @BindView(R.id.text_phone)
        TextView mTextPhone;
        @BindView(R.id.ic_delete)
        ImageView mImageDelete;
        @BindView(R.id.constraint_address)
        ConstraintLayout mConstraintAddress;
        @BindView(R.id.radio_address)
        RadioButton mRadioAddress;
        private Context mContext;
        private int mPosition;

        AddressViewHolder(@NonNull View itemView, OnClickAddressListener onClickAddressListener, Context context) {
            super(itemView);
            mOnClickAddressListener = onClickAddressListener;
            mContext = context;
            ButterKnife.bind(this, itemView);
            mConstraintAddress.setOnClickListener(this);
            mImageDelete.setOnClickListener(this);
            mRadioAddress.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ic_delete:
                    alert();
                    break;
                case R.id.constraint_address:
                    mOnClickAddressListener.onClickUpdateAddress(mAddressUser);
                    break;
                case R.id.radio_address:
                    mOnClickAddressListener.chooseAddress(mAddressUser);
                    mLastSelectedPosition = mPosition;
                    notifyDataSetChanged();
                    break;
            }
        }

        public void bindData(AddressUser addressUser, int position) {
            if (addressUser == null) {
                return;
            }
            mPosition = position;
            mAddressUser = addressUser;
            mTextAddress.setText(addressUser.getAddressOrder());
            mTextPhone.setText(addressUser.getPhoneNumber());
            mTextViewNameUser.setText(addressUser.getUserNameOrder());
            mRadioAddress.setChecked(mLastSelectedPosition == position);
        }

        private void alert() {
            AlertDialog.Builder b = new AlertDialog.Builder(mContext);
            b.setTitle(R.string.title_alert);
            b.setMessage(R.string.delete_address);
            b.setPositiveButton(R.string.title_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RealmAddress.deleteAddress(mAddressUser.getId());
                    mOnClickAddressListener.updateAddress();
                }
            });
            b.setNegativeButton(R.string.title_no, new DialogInterface.OnClickListener() {
                @Override

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            b.create().show();
        }

    }

    public interface OnClickAddressListener {
        void onClickUpdateAddress(AddressUser addressUser);

        void updateAddress();

        void chooseAddress(AddressUser addressUser);
    }
}
