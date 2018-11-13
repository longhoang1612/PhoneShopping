package hoanglong.thesis.graduation.juncomputer.screen.home.homefragment;

import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import hoanglong.thesis.graduation.juncomputer.screen.base.BasePresenter;

public class HomeContract {
    interface View {
        void onGetNewsFeedSuccess(NewsFeed newsFeed);

        void hideProgressBar();

        void onGetDataError(String error);
    }

    interface Presenter extends BasePresenter<HomeContract.View> {
        void getHome();
    }
}
