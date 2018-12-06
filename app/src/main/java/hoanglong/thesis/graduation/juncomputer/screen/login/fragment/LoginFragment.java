package hoanglong.thesis.graduation.juncomputer.screen.login.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmUser;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.UserInfoActivity;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.et_email)
    EditText mEditEmail;
    @BindView(R.id.et_password)
    EditText mEditPassword;
    @BindView(R.id.button_login)
    Button mButtonLogin;
    private ProgressDialog dialogProgress;
    private User user;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        if (!mEditEmail.getText().toString().isEmpty()
                || !mEditPassword.getText().toString().isEmpty()) {
            loginUser();
        } else {
            Toast.makeText(getContext(), "Bạn không được để trống thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();
        user = new User(email, password);
        Call<User> call = BaseService.getService().login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                hideProgress();
                RealmUser.saveUser(response.body());
                SharedPrefs.getInstance().put(Constant.Login.LOGIN_STATUS, true);
                Toast.makeText(getContext(), "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                showProgressSuccess();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                        getActivity().finish();
                    }
                }, 1000);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), "Đăng nhập thất bại ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressSuccess() {
        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Đăng nhập thành công...");
        dialogProgress.show();
    }

    public void showProgress() {
        if (dialogProgress != null) {
            return;
        }
        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Đăng nhập...");
        dialogProgress.show();
    }

    public void hideProgress() {
        if (dialogProgress == null)
            return;
        dialogProgress.dismiss();
        dialogProgress = null;
    }
}
