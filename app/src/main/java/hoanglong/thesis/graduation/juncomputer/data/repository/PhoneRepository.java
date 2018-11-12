package hoanglong.thesis.graduation.juncomputer.data.repository;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.data.source.PhoneDataSourceImp;
import hoanglong.thesis.graduation.juncomputer.data.source.remote.PhoneDataSource;

public class PhoneRepository implements PhoneDataSourceImp.localDataSource
        , PhoneDataSourceImp.remoteDataSource {
    private static PhoneRepository sInstance;
    private PhoneDataSource mPhoneDataSource;

    private PhoneRepository(PhoneDataSource phoneDataSource) {
        mPhoneDataSource = phoneDataSource;
    }

    public static PhoneRepository getInstance(PhoneDataSource phoneDataSource) {
        if (sInstance == null)
            sInstance = new PhoneRepository(phoneDataSource);
        return sInstance;
    }

    @Override
    public void getPhoneCategory(CallBack<List<ItemPhoneCategory>> callBack) {
        mPhoneDataSource.getPhoneCategory(callBack);
    }

    @Override
    public void getPhoneItem(CallBack<List<ItemPhoneProduct>> callBack) {
        mPhoneDataSource.getPhoneItem(callBack);
    }
}
