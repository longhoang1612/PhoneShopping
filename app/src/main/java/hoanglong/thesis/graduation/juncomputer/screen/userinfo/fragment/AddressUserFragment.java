package hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.payment.NewAddressFragment;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.adapter.AddressAdapter;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressUserFragment extends BaseFragment implements View.OnClickListener
        , AddressAdapter.OnClickAddressListener {

    public static final String TAG = AddressUserFragment.class.getName();
    @BindView(R.id.recycler_list_address)
    RecyclerView mRecyclerListAddress;
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    @BindView(R.id.relative_add_address)
    RelativeLayout mRelativeAddress;
    private User mUser;
    private List<AddressUser> mAddressUserList;
    private List<AddressUser> mAddressUsersRealm;
    @BindView(R.id.progress_address)
    ProgressBar mProgressAddress;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_address_user;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mImageBack.setOnClickListener(this);
        mRelativeAddress.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mProgressAddress.setVisibility(View.VISIBLE);
        mRecyclerListAddress.setVisibility(View.GONE);
        mAddressUserList = new ArrayList<>();
        mAddressUsersRealm = RealmAddress.getListAddress();
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get(Constant.Login.OBJECT_USER_LOGIN, String.class);
        mUser = gson.fromJson(json, User.class);
        if (mUser == null) {
            return;
        }
        onUpdateAddress();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.relative_add_address:
                if (getFragmentManager() != null) {
                    FragmentTransactionUtils.addFragment(getFragmentManager(),
                            new AddAddressFragment(),
                            R.id.frame_user_info,
                            NewAddressFragment.TAG, true, -1, -1);
                }
                break;
        }
    }

    public void onUpdateAddress() {
        uploadAddressToServer();
    }

    private void getUserProfile() {
        Call<User> call = BaseService.getService().getProfileUser(mUser.getEmail());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.body() != null) {
                    mProgressAddress.setVisibility(View.GONE);
                    mRecyclerListAddress.setVisibility(View.VISIBLE);
                    mAddressUserList = response.body().getAddress();
                    mRecyclerListAddress.setAdapter(new AddressAdapter(mAddressUserList,
                            AddressUserFragment.this));
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
            }
        });

    }

    @Override
    public void onClickUpdateAddress(AddressUser addressUser) {

    }

    @Override
    public void updateAddress() {

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
                if(getContext()==null) return;
                getUserProfile();
                Toasty.success(getContext(), "Lấy thông tin địa chỉ thành công").show();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                if(getContext()==null) return;
                Toasty.success(getContext(), "Lỗi cập nhật địa chỉ").show();
            }
        });
    }
}
