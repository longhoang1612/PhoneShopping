package hoanglong.thesis.graduation.juncomputer.service;

import hoanglong.thesis.graduation.juncomputer.data.model.detailPhone.AllDetailPhone;
import hoanglong.thesis.graduation.juncomputer.data.model.category.PhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.category.PhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.home.Home;
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

    @GET("/getHome")
    Call<Home> getHome();
}