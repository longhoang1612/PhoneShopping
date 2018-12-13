package hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUser;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmAddress;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.payment.listener.OnListenerPayment;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.listener.UpdateAddressListener;

public class AddAddressFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = AddAddressFragment.class.getName();
    public static final String BUNDLE_ADDRESS = "BUNDLE_ADDRESS";
    private static final String BUNDLE_IS_UPDATE = "BUNDLE_IS_UPDATE";
    @BindView(R.id.et_name)
    EditText mEditTextName;
    @BindView(R.id.et_phone)
    EditText mEditTextPhone;
    @BindView(R.id.et_provide)
    EditText mEditTextProvince;
    @BindView(R.id.et_quan)
    EditText mEditTextQuan;
    @BindView(R.id.et_phuong)
    EditText mEditTextPhuong;
    @BindView(R.id.et_address)
    EditText mEditTextAddress;
    @BindView(R.id.relative_save_address)
    RelativeLayout mRelativeSaveAddress;
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    private ProgressDialog mDialogProgress;
    private AddressUser mAddressUser;
    private boolean isUpdate;
    private UpdateAddressListener mUpdateAddressListener;

    public static AddAddressFragment newInstance(AddressUser addressUser) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_ADDRESS, addressUser);
        bundle.putBoolean(BUNDLE_IS_UPDATE, true);
        AddAddressFragment fragment = new AddAddressFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUpdateAddressListener = (UpdateAddressListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAddressUser = getArguments().getParcelable(BUNDLE_ADDRESS);
            isUpdate = true;
        }
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_new_address;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mRelativeSaveAddress.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        if (mAddressUser == null) {
            return;
        }

        String[] address = mAddressUser.getAddressOrder().split("-");

        mEditTextName.setText(mAddressUser.getUserNameOrder());
        mEditTextPhone.setText(mAddressUser.getPhoneNumber());
        mEditTextProvince.setText(address[3]);
        mEditTextQuan.setText(address[2]);
        mEditTextPhuong.setText(address[1]);
        mEditTextAddress.setText(address[0]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.relative_save_address:
                checkValid();
                break;
        }
    }

    private void checkValid() {
        String name = mEditTextName.getText().toString();
        String phone = mEditTextPhone.getText().toString();
        String province = mEditTextProvince.getText().toString();
        String country = mEditTextQuan.getText().toString();
        String ward = mEditTextPhuong.getText().toString();
        String address = mEditTextAddress.getText().toString();

        if (name.isEmpty()) {
            Toasty.warning(getContext(), "Bạn không được để trống tên", Toast.LENGTH_SHORT, true).show();
        } else if (phone.isEmpty()) {
            Toasty.warning(getContext(), "Bạn không được để trống số điện thoại ", Toast.LENGTH_SHORT, true).show();
        } else if (province.isEmpty()) {
            Toasty.warning(getContext(), "Bạn không được để trống Tỉnh/Thành", Toast.LENGTH_SHORT, true).show();
        } else if (country.isEmpty()) {
            Toasty.warning(getContext(), "Bạn không được để trống Quận/Huyện", Toast.LENGTH_SHORT, true).show();
        } else if (ward.isEmpty()) {
            Toasty.warning(getContext(), "Bạn không được để trống Phường/Xã ", Toast.LENGTH_SHORT, true).show();
        } else if (address.isEmpty()) {
            Toasty.warning(getContext(), "Bạn không được để trống địa chỉ", Toast.LENGTH_SHORT, true).show();
        } else if (phone.length() < 10) {
            Toasty.warning(getContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT, true).show();

        } else {
            int id = 0;
            showProgress();
            if (RealmAddress.getListAddress() != null) {
                id = RealmAddress.getListAddress().size() + 1;
            }

            AddressUser addressUser = new AddressUser(id,
                    phone, address + " - " + ward + " - " + country + " - " + province, name
            );
            saveAddress(addressUser);
        }
    }

    private void saveAddress(final AddressUser addressUser) {
        if (addressUser == null) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                Toasty.success(getContext(), "Bạn đã cập nhật địa chỉ thành công", Toast.LENGTH_SHORT, true).show();
                if (isUpdate) {
                    RealmAddress.updateAddress(addressUser);
                } else {
                    RealmAddress.addAddress(addressUser);
                }
                mUpdateAddressListener.onUpdateAddress();
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        }, 1000);
    }

    public void showProgress() {
        if (mDialogProgress != null) {
            return;
        }
        mDialogProgress = new ProgressDialog(getContext());
        mDialogProgress.setMessage("Lưu địa chỉ...");
        mDialogProgress.show();
    }

    public void hideProgress() {
        if (mDialogProgress == null)
            return;
        mDialogProgress.dismiss();
        mDialogProgress = null;
    }
}
