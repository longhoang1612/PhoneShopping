package hoanglong.thesis.graduation.juncomputer.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;

public class CartUpload {
    @SerializedName("cartCurrent")
    @Expose
    private List<CartItem> mCartItems = null;

    public CartUpload(List<CartItem> cartItems) {
        mCartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return mCartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        mCartItems = cartItems;
    }

    @Override
    public String toString() {
        return "CartUpload{" +
                "mCartItems=" + mCartItems +
                '}';
    }
}
