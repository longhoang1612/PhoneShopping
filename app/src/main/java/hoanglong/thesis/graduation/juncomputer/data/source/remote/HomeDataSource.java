package hoanglong.thesis.graduation.juncomputer.data.source.remote;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.data.source.HomeDataSourceImp;

public class HomeDataSource implements HomeDataSourceImp.localDataSource
        ,HomeDataSourceImp.remoteDataSource {

    private static HomeDataSource mInstance;

    public static HomeDataSource getInstance() {
        if (mInstance == null)
            mInstance = new HomeDataSource();
        return mInstance;
    }

    private void getNewsFeedFromApi(CallBack<NewsFeed> callBack) {
        new HomeAsyncTask(callBack).getHomeApi();
    }

    @Override
    public void getHome(CallBack<NewsFeed> callBack) {
        getNewsFeedFromApi(callBack);
    }
}
