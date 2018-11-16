package hoanglong.thesis.graduation.juncomputer.data.source.remote;

import android.support.annotation.NonNull;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.category.PhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneCategoryAsyncTask {
    private CallBack<List<ItemPhoneCategory>> mCallBack;

    PhoneCategoryAsyncTask(CallBack<List<ItemPhoneCategory>> mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void getCategoryPhone(String typeCategory) {
        Call<PhoneCategory> call = BaseService.getService().getTypeCategory(typeCategory);
        call.enqueue(new Callback<PhoneCategory>() {
            @Override
            public void onResponse(@NonNull Call<PhoneCategory> call,
                                   @NonNull Response<PhoneCategory> response) {
                if (response.body() != null) {
                    mCallBack.getDataSuccess(response.body().getPhoneCategoryList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhoneCategory> call, @NonNull Throwable t) {
                mCallBack.getDataError(t.getMessage());
            }
        });
    }
}
