package hoanglong.thesis.graduation.juncomputer.screen.home.homefragment;

import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import hoanglong.thesis.graduation.juncomputer.data.repository.HomeRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private HomeRepository mHomeRepository;

    public HomePresenter(HomeRepository homeRepository) {
        mHomeRepository = homeRepository;
    }

    @Override
    public void getHome() {
        mHomeRepository.getHome(new CallBack<NewsFeed>() {
            @Override
            public void getDataSuccess(NewsFeed data) {
                mView.onGetNewsFeedSuccess(data);
                mView.hideProgressBar();
            }

            @Override
            public void getDataError(String error) {
                mView.onGetDataError(error);
            }
        });
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
