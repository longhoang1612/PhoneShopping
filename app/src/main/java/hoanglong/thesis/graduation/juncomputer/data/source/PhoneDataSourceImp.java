package hoanglong.thesis.graduation.juncomputer.data.source;

import java.util.List;
import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;

public interface PhoneDataSourceImp {
    interface remoteDataSource {
        void getPhoneCategory(CallBack<List<ItemPhoneCategory>> callBack,String typeCategory);

        void getPhoneItem(CallBack<List<ItemPhoneProduct>> callBack,String typeCategory);
    }

    interface localDataSource {
    }
}
