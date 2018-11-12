package hoanglong.thesis.graduation.juncomputer.screen.phone.phone_category;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.repository.PhoneRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;

public class PhoneCategoryPresenter implements PhoneCategoryContract.Presenter {

    private PhoneRepository mPhoneRepository;
    private PhoneCategoryContract.View mView;

    PhoneCategoryPresenter(PhoneRepository phoneRepository) {
        mPhoneRepository = phoneRepository;
    }

    @Override
    public void getCategories() {
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
        });
    }

    @Override
    public void getPhones() {
        mPhoneRepository.getPhoneItem(new CallBack<List<ItemPhoneProduct>>() {
            @Override
            public void getDataSuccess(List<ItemPhoneProduct> data) {
                if (data == null)
                    return;
                mView.onGetDataPhoneSuccess(data);
                mView.hideProgressPhone();
            }

            @Override
            public void getDataError(String error) {
                mView.onGetDataPhoneError(error);
                mView.hideProgressPhone();
            }
        });
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
