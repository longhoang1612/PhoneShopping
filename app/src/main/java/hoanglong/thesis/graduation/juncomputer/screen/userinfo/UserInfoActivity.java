package hoanglong.thesis.graduation.juncomputer.screen.userinfo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseActivity;
import hoanglong.thesis.graduation.juncomputer.screen.favorites.FavoritesFragment;
import hoanglong.thesis.graduation.juncomputer.screen.manageOrder.ManagerOrderFragment;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment.AddressUserFragment;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment.ChangeInfoUserFragment;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment.SeenFragment;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.listener.UpdateAddressListener;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener
        , UpdateAddressListener, ChangeInfoUserFragment.OnUpdateInfoListener {

    @BindView(R.id.relative_manager_order)
    RelativeLayout mRelativeOrder;
    @BindView(R.id.relative_manage_address)
    RelativeLayout mRelativeAddress;
    @BindView(R.id.relative_manage_seen)
    RelativeLayout mRelativeSeen;
    @BindView(R.id.relative_manage_favorites)
    RelativeLayout mRelativeFavorites;
    @BindView(R.id.constraint_info_user)
    ConstraintLayout mConstraintUserInfo;
    @BindView(R.id.text_name_user)
    TextView mTextNameUser;
    @BindView(R.id.text_email_user)
    TextView mTextEmailUser;
    @BindView(R.id.text_date_join)
    TextView mTextDateJoinUser;
    @BindView(R.id.ic_back)
    ImageView mIcBack;
    @BindView(R.id.relative_log_out)
    RelativeLayout mRelativeLogOut;
    private ProgressDialog mDialogProgress;
    private Fragment mFragment;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        mRelativeAddress.setOnClickListener(this);
        mRelativeOrder.setOnClickListener(this);
        mRelativeSeen.setOnClickListener(this);
        mRelativeFavorites.setOnClickListener(this);
        mConstraintUserInfo.setOnClickListener(this);
        mIcBack.setOnClickListener(this);
        mRelativeLogOut.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        updateInfo();
    }

    private void updateInfo() {
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get(Constant.Login.OBJECT_USER_LOGIN, String.class);
        User user = gson.fromJson(json, User.class);
        if (user == null) {
            return;
        }
        mTextNameUser.setText(user.getFullName());
        mTextEmailUser.setText(user.getEmail());
        mTextDateJoinUser.setText(String.format(getString(R.string.title_time_user), user.getDateJoin()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_manage_address:
                mFragment = new AddressUserFragment();
                openFragment(mFragment, AddressUserFragment.TAG);
                break;
            case R.id.relative_manager_order:
                openFragment(new ManagerOrderFragment(), ManagerOrderFragment.TAG);
                break;
            case R.id.relative_manage_seen:
                openFragment(new SeenFragment(), SeenFragment.TAG);
                break;
            case R.id.relative_manage_favorites:
                openFragment(new FavoritesFragment(), FavoritesFragment.TAG);
                break;
            case R.id.constraint_info_user:
                openFragment(new ChangeInfoUserFragment(), ChangeInfoUserFragment.TAG);
                break;
            case R.id.ic_back:
                onBackPressed();
                break;
            case R.id.relative_log_out:
                showProgress();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                        SharedPrefs.getInstance().clear();
                        finish();
                    }
                }, 500);
                break;
        }
    }

    public void showProgress() {
        if (mDialogProgress != null) {
            return;
        }
        mDialogProgress = new ProgressDialog(UserInfoActivity.this);
        mDialogProgress.setMessage(getString(R.string.alert_sign_out));
        mDialogProgress.show();
    }

    public void hideProgress() {
        if (mDialogProgress == null)
            return;
        mDialogProgress.dismiss();
        mDialogProgress = null;
    }

    public void openFragment(Fragment fragment, String tag) {
        FragmentTransactionUtils.addFragment(
                getSupportFragmentManager(),
                fragment, R.id.frame_user_info, tag,
                true, -1, -1);
    }

    @Override
    public void onUpdateAddress() {
        if (mFragment instanceof AddressUserFragment) {
            ((AddressUserFragment) mFragment).onUpdateAddress();
        }
    }

    @Override
    public void onUpdateInfo() {
        updateInfo();
    }
}
