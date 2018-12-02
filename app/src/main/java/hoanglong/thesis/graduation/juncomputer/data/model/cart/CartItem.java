package hoanglong.thesis.graduation.juncomputer.data.model.cart;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CartItem extends RealmObject {

    private String mName;
    private String mPrice;
    private int mQuantity;
    private String mImage;
    @PrimaryKey
    private String mId;

    public CartItem(String name, String price, int quantity, String image, String id) {
        mName = name;
        mPrice = price;
        mQuantity = quantity;
        mImage = image;
        mId = id;
    }

    public CartItem() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}
