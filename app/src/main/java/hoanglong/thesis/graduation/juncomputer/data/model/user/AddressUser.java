package hoanglong.thesis.graduation.juncomputer.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class AddressUser extends RealmObject {
    @SerializedName("phoneNumber")
    @Expose
    private String mPhoneNumber;
    @SerializedName("addressOrder")
    @Expose
    private String mAddressOrder;
    @SerializedName("userNameOrder")
    @Expose
    private String mUserNameOrder;

    public AddressUser() {
    }

    public AddressUser(String phoneNumber, String addressOrder, String userNameOrder) {
        mPhoneNumber = phoneNumber;
        mAddressOrder = addressOrder;
        mUserNameOrder = userNameOrder;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getAddressOrder() {
        return mAddressOrder;
    }

    public void setAddressOrder(String addressOrder) {
        mAddressOrder = addressOrder;
    }

    public String getUserNameOrder() {
        return mUserNameOrder;
    }

    public void setUserNameOrder(String userNameOrder) {
        mUserNameOrder = userNameOrder;
    }
}
