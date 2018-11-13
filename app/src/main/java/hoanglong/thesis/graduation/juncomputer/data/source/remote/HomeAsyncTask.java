package hoanglong.thesis.graduation.juncomputer.data.source.remote;

import android.support.annotation.NonNull;

import hoanglong.thesis.graduation.juncomputer.data.model.home.Home;
import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAsyncTask {
    private CallBack<NewsFeed> mCallBack;

    public HomeAsyncTask(CallBack<NewsFeed> callBack) {
        mCallBack = callBack;
    }


    public void getHomeApi() {
        Call<Home> call = BaseService.getService().getHome();
        call.enqueue(new Callback<Home>() {
            @Override
            public void onResponse(@NonNull Call<Home> call, @NonNull Response<Home> response) {
                if (response.body() != null) {
                    mCallBack.getDataSuccess(response.body().getNewsFeed().get(0));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Home> call, @NonNull Throwable t) {
                mCallBack.getDataError(t.getMessage());
            }
        });
    }
}
