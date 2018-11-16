package hoanglong.thesis.graduation.juncomputer.data.repository;

import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.data.source.HomeDataSourceImp;
import hoanglong.thesis.graduation.juncomputer.data.source.remote.HomeDataSource;
import retrofit2.Call;

public class HomeRepository implements HomeDataSourceImp.remoteDataSource
        , HomeDataSourceImp.localDataSource {

    private static HomeRepository sInstance;
    private HomeDataSource mHomeDataSource;

    private HomeRepository(HomeDataSource homeDataSource) {
        mHomeDataSource = homeDataSource;
    }

    public static HomeRepository getInstance(HomeDataSource homeDataSource) {
        if (sInstance == null)
            sInstance = new HomeRepository(homeDataSource);
        return sInstance;
    }

    @Override
    public void getHome(CallBack<NewsFeed> callBack) {
        mHomeDataSource.getHome(callBack);
    }

}
