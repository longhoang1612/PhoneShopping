package hoanglong.thesis.graduation.juncomputer.data.source.remote;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.PhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAsyncTask {

    private CallBack<List<ItemPhoneProduct>> mCallBack;

    PhoneAsyncTask(CallBack<List<ItemPhoneProduct>> mCallBack) {
        this.mCallBack = mCallBack;
    }

    void getDataPhone(){
        Call<PhoneProduct> call = BaseService.getService().getAllPhone();
        call.enqueue(new Callback<PhoneProduct>() {
            @Override
            public void onResponse(@NonNull Call<PhoneProduct> call,
                                   @NonNull Response<PhoneProduct> response) {
                if (response.body() != null) {
                    mCallBack.getDataSuccess(response.body().getPhoneProduct());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhoneProduct> call, @NonNull Throwable t) {
               mCallBack.getDataError(t.getMessage());
            }
        });
    }
}
