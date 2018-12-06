package hoanglong.thesis.graduation.juncomputer.screen.userinfo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmUser;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseActivity;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.relative_manager_order)
    RelativeLayout mRelativeOrder;
    @BindView(R.id.relative_manage_address)
    RelativeLayout mRelativeAddress;
    @BindView(R.id.relative_manage_product_buy)
    RelativeLayout mRelativeBuy;
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
    private User user;
    private ProgressDialog dialogProgress;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
        mRelativeAddress.setOnClickListener(this);
        mRelativeOrder.setOnClickListener(this);
        mRelativeBuy.setOnClickListener(this);
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
        if (RealmUser.getUser() == null) {
            return;
        }
        user = RealmUser.getUser();
        mTextNameUser.setText(user.getFullName());
        mTextEmailUser.setText(user.getEmail());
        mTextDateJoinUser.setText(String.format("Thành viên từ %s", user.getDateJoin()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_manage_address:
                break;
            case R.id.relative_manager_order:
                break;
            case R.id.relative_manage_seen:
                break;
            case R.id.relative_manage_product_buy:
                break;
            case R.id.relative_manage_favorites:
                break;
            case R.id.constraint_info_user:
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
                        RealmUser.signOut(user);
                        finish();
                    }
                }, 500);
                break;
        }
    }

    public void showProgress() {
        if (dialogProgress != null) {
            return;
        }
        dialogProgress = new ProgressDialog(UserInfoActivity.this);
        dialogProgress.setMessage("Đăng xuất...");
        dialogProgress.show();
    }

    public void hideProgress() {
        if (dialogProgress == null)
            return;
        dialogProgress.dismiss();
        dialogProgress = null;
    }
}
