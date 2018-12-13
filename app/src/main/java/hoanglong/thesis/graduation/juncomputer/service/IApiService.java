package hoanglong.thesis.graduation.juncomputer.service;

import hoanglong.thesis.graduation.juncomputer.data.model.category.PhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.home.Home;
import hoanglong.thesis.graduation.juncomputer.data.model.order.Order;
import hoanglong.thesis.graduation.juncomputer.data.model.order.OrderUpload;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.PhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUpload;
import hoanglong.thesis.graduation.juncomputer.data.model.user.CartUpload;
import hoanglong.thesis.graduation.juncomputer.data.model.user.FavoritesUpload;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @POST("/register")
    Call<User> register(@Body User user);

    @POST("/login")
    Call<User> login(@Body User user);

    @PUT("/updateFavorites/{email}")
    Call<User> updateFavorites(@Path("email") String email, @Body FavoritesUpload favoritesUpload);

    @PUT("/updateAddressUser/{email}")
    Call<User> updateAddress(@Path("email") String email, @Body AddressUpload addressUpload);

    @GET("/getUserProfile/{email}")
    Call<User> getProfileUser(@Path("email") String email);

    @POST("/createOrder")
    Call<Order> createOrder(@Body Order order);

    @GET("/getOrder/{idUser}")
    Call<OrderUpload> getOrderByUser(@Path("idUser") String idUser);

    @PUT("/updateCartCurrent/{email}")
    Call<User> updateCartCurrent(@Path("email") String email, @Body CartUpload cartUpload);
}