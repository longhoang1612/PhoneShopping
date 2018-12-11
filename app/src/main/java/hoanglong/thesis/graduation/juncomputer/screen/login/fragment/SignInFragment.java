package hoanglong.thesis.graduation.juncomputer.screen.login.fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmUser;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SignInFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.et_hoten)
    EditText mEditFullName;
    @BindView(R.id.et_email)
    EditText mEditEmail;
    @BindView(R.id.et_password)
    EditText mEditPassword;
    @BindView(R.id.radio_group)
    RadioGroup mRadioSex;
    @BindView(R.id.button_sign_in)
    Button mButtonSignIn;
    private ProgressDialog dialogProgress;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mButtonSignIn.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                showProgress();
                check();
                break;
        }
    }

    private void check() {
        if (mEditEmail.getText().toString().isEmpty()
                || mEditFullName.getText().toString().isEmpty() || mEditPassword.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn không được để trống thông tin", Toast.LENGTH_SHORT).show();
        } else {
            createUser();
        }
    }

    private void createUser() {
        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();
        String sex = null;
        switch (mRadioSex.getCheckedRadioButtonId()) {
            case R.id.radioButton_male:
                sex = "Nam";
                break;
            case R.id.radioButton_female:
                sex = "Nữ";
                break;
        }
        String fullName = mEditFullName.getText().toString();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = df.format(c);

        final User user = new User(email, password, sex, fullName, currentDate);

        Call<User> call = BaseService.getService().register(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                hideProgress();

                //RealmUser.saveUser(user);

//                SharedPreferences mPrefs = getActivity().getPreferences(MODE_PRIVATE);
//                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(response.body());
                SharedPrefs.getInstance().put(Constant.Login.OBJECT_USER_LOGIN, json);

                SharedPrefs.getInstance().put(Constant.Login.LOGIN_STATUS, true);
                Toast.makeText(getContext(), "REGISTER SUCCESS", Toast.LENGTH_SHORT).show();
                showProgressSuccess();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                        getActivity().finish();
                    }
                }, 2000);

            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                hideProgress();
            }
        });
    }

    public void showProgressSuccess() {
        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Tiếp tục shopping thôi...");
        dialogProgress.show();
    }

    public void showProgress() {
        if (dialogProgress != null) {
            return;
        }
        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Signing...");
        dialogProgress.show();
    }

    public void hideProgress() {
        if (dialogProgress == null)
            return;
        dialogProgress.dismiss();
        dialogProgress = null;
    }

}
