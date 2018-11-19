package hoanglong.thesis.graduation.juncomputer.screen.phone.phone_category;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.repository.PhoneRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;

public class PhoneCategoryPresenter implements PhoneCategoryContract.Presenter {

    private PhoneRepository mPhoneRepository;
    private PhoneCategoryContract.View mView;

    PhoneCategoryPresenter(PhoneRepository phoneRepository) {
        mPhoneRepository = phoneRepository;
    }

    @Override
    public void getCategories(String typeCategory) {
        mPhoneRepository.getPhoneCategory(new CallBack<List<ItemPhoneCategory>>() {
            @Override
            public void getDataSuccess(List<ItemPhoneCategory> data) {
                if (data == null) {
                    return;
                }
                mView.onGetDataSuccess(data);
                mView.hideProgressBar();
            }

            @Override
            public void getDataError(String error) {
                mView.onGetDataError(error);
                mView.hideProgressBar();
            }
        }, typeCategory);
    }

    @Override
    public void getPhones(String typeCategory) {
        mPhoneRepository.getPhoneItem(new CallBack<List<ItemPhoneProduct>>() {
            @Override
            public void getDataSuccess(List<ItemPhoneProduct> data) {
                if (data == null || data.size() == 0)
                    return;
                mView.onGetDataPhoneSuccess(data);
                mView.hideProgressPhone();
            }

            @Override
            public void getDataError(String error) {
                mView.onGetDataPhoneError(error);
                mView.hideProgressPhone();
            }
        }, typeCategory);
    }

    @Override
    public void setView(PhoneCategoryContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
