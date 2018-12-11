package hoanglong.thesis.graduation.juncomputer.data.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AddressUser extends RealmObject implements Parcelable {
    @PrimaryKey
    private int mId;
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

    public AddressUser(int id, String phoneNumber, String addressOrder, String userNameOrder) {
        mId = id;
        mPhoneNumber = phoneNumber;
        mAddressOrder = addressOrder;
        mUserNameOrder = userNameOrder;
    }

    private AddressUser(Parcel in) {
        mId = in.readInt();
        mPhoneNumber = in.readString();
        mAddressOrder = in.readString();
        mUserNameOrder = in.readString();
    }

    public static final Creator<AddressUser> CREATOR = new Creator<AddressUser>() {
        @Override
        public AddressUser createFromParcel(Parcel in) {
            return new AddressUser(in);
        }

        @Override
        public AddressUser[] newArray(int size) {
            return new AddressUser[size];
        }
    };

    public AddressUser(AddressUserBuilder addressUserBuilder) {
        addressUserBuilder.mAddressOrder = mAddressOrder;
        addressUserBuilder.mPhoneNumber = mPhoneNumber;
        addressUserBuilder.mUserNameOrder = mUserNameOrder;
        addressUserBuilder.mId = mId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPhoneNumber);
        dest.writeString(mAddressOrder);
        dest.writeString(mUserNameOrder);
        dest.writeInt(mId);
    }

    public static class AddressUserBuilder {
        private String mPhoneNumber;
        private String mAddressOrder;
        private String mUserNameOrder;
        private int mId;

        public AddressUserBuilder setPhoneNumber(String phoneNumber) {
            mPhoneNumber = phoneNumber;
            return this;
        }

        public AddressUserBuilder setAddressOrder(String addressOrder) {
            mAddressOrder = addressOrder;
            return this;
        }

        public AddressUserBuilder setUserNameOrder(String userNameOrder) {
            mUserNameOrder = userNameOrder;
            return this;
        }

        public AddressUserBuilder setId(int id) {
            mId = id;
            return this;
        }

        public AddressUser build() {
            return new AddressUser(this);
        }
    }

    @Override
    public String toString() {
        return "AddressUser{" +
                "mId=" + mId +
                ", mPhoneNumber='" + mPhoneNumber + '\'' +
                ", mAddressOrder='" + mAddressOrder + '\'' +
                ", mUserNameOrder='" + mUserNameOrder + '\'' +
                '}';
    }
}
