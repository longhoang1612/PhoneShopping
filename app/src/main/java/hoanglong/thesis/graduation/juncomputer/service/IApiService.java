package hoanglong.thesis.graduation.juncomputer.service;

import hoanglong.thesis.graduation.juncomputer.data.model.detailPhone.AllDetailPhone;
import hoanglong.thesis.graduation.juncomputer.data.model.PhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.PhoneProduct;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiService {
    @GET("/getAllCategoryPhone")
    Call<PhoneCategory> getCategoryPhone();

    @GET("/getPhoneProduct/{type}")
    Call<PhoneProduct> getPhoneProduct(@Path("type") String type);

    @GET("/getPhoneProduct")
    Call<PhoneProduct> getAllPhone();

    @GET("/getDetailPhoneItem")
    Call<AllDetailPhone> getAllDetailPhone();
}