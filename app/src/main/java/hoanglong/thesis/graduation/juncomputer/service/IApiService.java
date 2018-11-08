package hoanglong.thesis.graduation.juncomputer.service;

import hoanglong.thesis.graduation.juncomputer.model.DetailPhone.AllDetailPhone;
import hoanglong.thesis.graduation.juncomputer.model.PhoneCategory;
import hoanglong.thesis.graduation.juncomputer.model.PhoneProduct;
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