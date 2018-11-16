package hoanglong.thesis.graduation.juncomputer.data.source;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import retrofit2.Call;

public interface HomeDataSourceImp {
    interface remoteDataSource {
        void getHome(CallBack<NewsFeed> callBack);
    }

    interface localDataSource {
    }
}
