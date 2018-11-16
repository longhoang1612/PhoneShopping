package hoanglong.thesis.graduation.juncomputer.screen.home.homefragment;

import android.util.Log;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import hoanglong.thesis.graduation.juncomputer.data.repository.CategoryRepository;
import hoanglong.thesis.graduation.juncomputer.data.repository.HomeRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private HomeRepository mHomeRepository;
    private CategoryRepository mCategoryRepository;

    HomePresenter(HomeRepository homeRepository, CategoryRepository categoryRepository) {
        mHomeRepository = homeRepository;
        mCategoryRepository = categoryRepository;
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
    public void getCategoryHome() {
        mCategoryRepository.getCategory(new CallBack<List<Category>>() {
            @Override
            public void getDataSuccess(List<Category> data) {
                mView.onGetCategoryHomeSuccess(data);
            }

            @Override
            public void getDataError(String error) {
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
