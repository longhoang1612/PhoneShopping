package hoanglong.thesis.graduation.juncomputer.screen.category;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.data.repository.CategoryRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;

public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View mView;
    private CategoryRepository mCategoryRepository;

    public CategoryPresenter(CategoryRepository categoryRepository) {
        mCategoryRepository = categoryRepository;
    }

    @Override
    public void getCategories() {
        mCategoryRepository.getCategory(new CallBack<List<Category>>() {
            @Override
            public void getDataSuccess(List<Category> data) {
                mView.onGetDataSuccess(data);
                mView.hideProgressBar();
            }

            @Override
            public void getDataError(String error) {
                mView.hideProgressBar();
                mView.onGetDataError(error);
            }
        });
    }

    @Override
    public void setView(CategoryContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
