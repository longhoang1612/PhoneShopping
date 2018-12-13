package hoanglong.thesis.graduation.juncomputer.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.model.user.CartUpload;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadCart {
    public static void uploadCart() {
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get(Constant.Login.OBJECT_USER_LOGIN, String.class);
        User user = gson.fromJson(json, User.class);
        if (user == null) {
            return;
        }
        List<CartItem> cartItems = new ArrayList<>();
        List<CartItem> mCartItemsRealm = RealmCart.getCartOffline();

        for (int i = 0; i < mCartItemsRealm.size(); i++) {
            CartItem cartItem = mCartItemsRealm.get(i);
            CartItem cartUpload = new CartItem(cartItem.getName(), cartItem.getPrice(), cartItem.getQuantity(), cartItem.getImage());
            cartItems.add(cartUpload);
        }

        CartUpload cartUpload = new CartUpload(cartItems);
        Call<User> call = BaseService.getService().updateCartCurrent(user.getEmail(), cartUpload);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
            }
        });
    }
}
