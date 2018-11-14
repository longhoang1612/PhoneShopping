package hoanglong.thesis.graduation.juncomputer.screen.phone.phone_category;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.screen.base.BasePresenter;

class PhoneCategoryContract {
    interface View {
        void onGetDataSuccess(List<ItemPhoneCategory> categories);

        void hideProgressBar();

        void onGetDataError(String error);

        void onGetDataPhoneSuccess(List<ItemPhoneProduct> products);

        void onGetDataPhoneError(String error);

        void hideProgressPhone();
    }

    interface Presenter extends BasePresenter<PhoneCategoryContract.View> {
        void getCategories();

        void getPhones();
    }
}
