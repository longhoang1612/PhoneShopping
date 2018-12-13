package hoanglong.thesis.graduation.juncomputer.screen.payment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUpload;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUser;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmAddress;
import hoanglong.thesis.graduation.juncomputer.listener.UpdateStep;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseActivity;
import hoanglong.thesis.graduation.juncomputer.screen.payment.adapter.AddressAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.payment.listener.OnListenerPayment;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends BaseActivity implements View.OnClickListener,
        UpdateStep, OnListenerPayment, AddressAdapter.OnClickAddressListener {

    @BindView(R.id.relative_add_new_location)
    RelativeLayout mRelativeAddNewLocation;
    @BindView(R.id.relative_continue)
    RelativeLayout mRelativeContinue;
    @BindView(R.id.text_title_fragment)
    TextView mTextTitle;
    @BindView(R.id.relative_step2)
    RelativeLayout mRelativeStep2;
    @BindView(R.id.relative_step3)
    RelativeLayout mRelativeStep3;
    @BindView(R.id.recycler_address)
    RecyclerView mRecyclerAddress;
    private List<AddressUser> mAddressUsersRealm;
    private AddressUser mAddressChoose;
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    private User mUser;
    private List<AddressUser> mAddressUserList;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
        mRelativeAddNewLocation.setOnClickListener(this);
        mRelativeContinue.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get(Constant.Login.OBJECT_USER_LOGIN, String.class);
        mUser = gson.fromJson(json, User.class);
        if (mUser == null) {
            return;
        }
        mAddressUserList = new ArrayList<>();
        mAddressUsersRealm = new ArrayList<>();
        updateAddress();
        mTextTitle.setText(getString(R.string.title_address_order));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_add_new_location:
                FragmentTransactionUtils.addFragment(getSupportFragmentManager(),
                        new NewAddressFragment(),
                        R.id.frame_add_address,
                        NewAddressFragment.TAG, true, -1, -1);
                break;
            case R.id.relative_continue:
                if (mAddressChoose != null) {
                    mRelativeContinue.setClickable(true);
                    mRelativeContinue.setBackgroundResource(R.drawable.custom_button1);
                    mRelativeStep2.setBackgroundResource(R.drawable.circle_2);
                    mTextTitle.setText(getString(R.string.title_payment));
                    FragmentTransactionUtils.addFragment(getSupportFragmentManager(),
                            PaymentFragment.newInstance(mAddressChoose),
                            R.id.frame_step,
                            NewAddressFragment.TAG, true, -1, -1);
                } else {
                    mRelativeContinue.setClickable(false);
                    mRelativeContinue.setBackgroundResource(R.drawable.custom_button4);
                }
                break;
            case R.id.ic_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void updateStep() {
        mRelativeStep2.setBackgroundResource(R.drawable.circle_off);
        mRelativeStep3.setBackgroundResource(R.drawable.circle_off);
        mTextTitle.setText(getString(R.string.title_address_order));
    }

    @Override
    public void updateStep3() {
        mRelativeStep3.setBackgroundResource(R.drawable.circle_2);
        mTextTitle.setText(getString(R.string.title_confirm));
    }

    @Override
    public void backStep3() {
        mTextTitle.setText(getString(R.string.title_payment));
        mRelativeStep3.setBackgroundResource(R.drawable.circle_off);
    }

    @Override
    public void onUpdateAddress() {
        updateAddress();
    }

    @Override
    public void updateAddress() {
        getUserProfile();
        mAddressUsersRealm = RealmAddress.getListAddress();
        uploadAddressToServer();
        if (mAddressChoose == null) {
            mRelativeContinue.setClickable(false);
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button4);
        } else {
            mRelativeContinue.setClickable(true);
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button1);
        }
    }

    private void uploadAddressToServer() {
        List<AddressUser> addressUserList = new ArrayList<>();
        for (int i = 0; i < mAddressUsersRealm.size(); i++) {
            AddressUser addressUser = mAddressUsersRealm.get(i);
            AddressUser addressUpload = new AddressUser(
                    addressUser.getPhoneNumber(),
                    addressUser.getAddressOrder(),
                    addressUser.getUserNameOrder());
            addressUserList.add(addressUpload);
        }

        AddressUpload addressUpload = new AddressUpload(addressUserList);

        Call<User> call = BaseService.getService().updateAddress(mUser.getEmail(), addressUpload);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (getApplicationContext() == null) return;
                getUserProfile();
                Toasty.success(getApplicationContext(), "Lấy thông tin địa chỉ thành công").show();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                if (getApplicationContext() == null) return;
                Toasty.success(getApplicationContext(), "Lỗi cập nhật địa chỉ").show();
            }
        });
    }

    @Override
    public void chooseAddress(AddressUser addressUser) {
        mAddressChoose = addressUser;
        if (mAddressChoose == null) {
            mRelativeContinue.setClickable(false);
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button4);
        } else {
            mRelativeContinue.setClickable(true);
            mRelativeContinue.setBackgroundResource(R.drawable.custom_button1);
        }
    }

    @Override
    public void onClickUpdateAddress(AddressUser addressUser) {
        FragmentTransactionUtils.addFragment(getSupportFragmentManager(),
                NewAddressFragment.newInstance(addressUser),
                R.id.frame_add_address,
                NewAddressFragment.TAG, true, -1, -1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void getUserProfile() {
        Call<User> call = BaseService.getService().getProfileUser(mUser.getEmail());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.body() != null) {
                    mRecyclerAddress.setVisibility(View.VISIBLE);
                    mAddressUserList = response.body().getAddress();
                    mRecyclerAddress.setAdapter(new AddressAdapter(mAddressUserList,
                            PaymentActivity.this));
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
            }
        });

    }
}
