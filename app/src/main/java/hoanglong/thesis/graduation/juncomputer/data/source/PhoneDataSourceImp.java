package hoanglong.thesis.graduation.juncomputer.data.source;

import java.util.List;
import hoanglong.thesis.graduation.juncomputer.data.model.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.ItemPhoneProduct;

public interface PhoneDataSourceImp {
    interface remoteDataSource {
        void getPhoneCategory(CallBack<List<ItemPhoneCategory>> callBack);

        void getPhoneItem(CallBack<List<ItemPhoneProduct>> callBack);
    }

    interface localDataSource {
    }
}
