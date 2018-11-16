package hoanglong.thesis.graduation.juncomputer.service;

import hoanglong.thesis.graduation.juncomputer.data.model.category.PhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.home.Home;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.PhoneProduct;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApiService {
    @GET("/getPhoneProduct")
    Call<PhoneProduct> getAllPhone();

    @GET("/getHome")
    Call<Home> getHome();

    @GET("/getPhoneWithType/{type}")
    Call<PhoneProduct> getPhoneWithType(@Path("type") String type);

    @GET("/getPhoneProduct/{title}")
    Call<PhoneProduct> getPhoneWithTitle(@Path("title") String title);

    @GET("/getPhoneCategory/{typeCategory}")
    Call<PhoneProduct> getItemWithCategory(@Path("typeCategory") String typeCategory);

    @GET("getCategory/{typeCategory}")
    Call<PhoneCategory> getTypeCategory(@Path("typeCategory") String typeCategory);
}