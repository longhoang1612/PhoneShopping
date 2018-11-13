package hoanglong.thesis.graduation.juncomputer.data.source.remote;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.data.source.PhoneDataSourceImp;

public class PhoneDataSource implements PhoneDataSourceImp.remoteDataSource
        , PhoneDataSourceImp.localDataSource {

    private static PhoneDataSource mInstance;

    public static PhoneDataSource getInstance() {
        if (mInstance == null)
            mInstance = new PhoneDataSource();
        return mInstance;
    }

    private void getDataCategoryFromApi(CallBack<List<ItemPhoneCategory>> callBack) {
        new PhoneCategoryAsyncTask(callBack).getCategoryPhone();
    }

    private void getPhoneFromApi(CallBack<List<ItemPhoneProduct>> callBack) {
        new PhoneAsyncTask(callBack).getDataPhone();
    }

    @Override
    public void getPhoneCategory(CallBack<List<ItemPhoneCategory>> callBack) {
        getDataCategoryFromApi(callBack);
    }

    @Override
    public void getPhoneItem(CallBack<List<ItemPhoneProduct>> callBack) {
        getPhoneFromApi(callBack);
    }
}
