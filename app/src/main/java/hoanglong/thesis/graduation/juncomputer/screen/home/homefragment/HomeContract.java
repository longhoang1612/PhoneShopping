package hoanglong.thesis.graduation.juncomputer.screen.home.homefragment;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import hoanglong.thesis.graduation.juncomputer.screen.base.BasePresenter;

public class HomeContract {
    interface View {
        void onGetNewsFeedSuccess(NewsFeed newsFeed);

        void hideProgressBar();

        void onGetDataError(String error);

        void onGetCategoryHomeSuccess(List<Category> categories);
    }

    interface Presenter extends BasePresenter<HomeContract.View> {
        void getHome();

        void getCategoryHome();
    }
}
