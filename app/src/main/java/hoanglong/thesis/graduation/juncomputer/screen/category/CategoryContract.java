package hoanglong.thesis.graduation.juncomputer.screen.category;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.screen.base.BasePresenter;

public class CategoryContract {
    public interface View {
        void onGetDataSuccess(List<Category> categories);

        void hideProgressBar();

        void onGetDataError(String error);
    }

    interface Presenter extends BasePresenter<View> {
        void getCategories();
    }
}
