package hoanglong.thesis.graduation.juncomputer.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;

public class User{

    @SerializedName("email")
    @Expose
    private String mEmail;
    @SerializedName("password")
    @Expose
    private String mPassword;
    @SerializedName("sex")
    @Expose
    private String mSex;
    @SerializedName("fullname")
    @Expose
    private String mFullName;
    @SerializedName("_id")
    @Expose
    private String mId;
    @SerializedName("dateJoin")
    @Expose
    private String mDateJoin;
    @SerializedName("favorites")
    @Expose
    private List<Favorites> mFavorites = null;
    @SerializedName("address")
    @Expose
    private List<AddressUser> mAddress = null;
    @SerializedName("cartCurrent")
    @Expose
    private List<CartItem> mCartItems = null;
    @SerializedName("birthday")
    @Expose
    private String mBirthDay;

    public User() {
    }

    public User(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public User(String email, String password, String sex, String fullName, String id, String dateJoin, List<Favorites> favorites, List<AddressUser> address, List<CartItem> cartItems) {
        mEmail = email;
        mPassword = password;
        mSex = sex;
        mFullName = fullName;
        mId = id;
        mDateJoin = dateJoin;
        mFavorites = favorites;
        mAddress = address;
        mCartItems = cartItems;
    }

    public User(String email, String password, String sex, String fullName, String id, String dateJoin, List<Favorites> favorites, List<AddressUser> address, List<CartItem> cartItems, String birthDay) {
        mEmail = email;
        mPassword = password;
        mSex = sex;
        mFullName = fullName;
        mId = id;
        mDateJoin = dateJoin;
        mFavorites = favorites;
        mAddress = address;
        mCartItems = cartItems;
        mBirthDay = birthDay;
    }

    public User(String email, String password, String sex, String fullName, String dateJoin, List<Favorites> favorites, List<AddressUser> address) {
        mEmail = email;
        mPassword = password;
        mSex = sex;
        mFullName = fullName;
        mDateJoin = dateJoin;
        mFavorites = favorites;
        mAddress = address;
    }

    public User(String email, String password, String sex, String fullName, String dateJoin) {
        mEmail = email;
        mPassword = password;
        mSex = sex;
        mFullName = fullName;
        mDateJoin = dateJoin;
    }

    public User(String sex, String fullName, String birthDay) {
        mSex = sex;
        mFullName = fullName;
        mBirthDay = birthDay;
    }

    public List<CartItem> getCartItems() {
        return mCartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        mCartItems = cartItems;
    }

    public User(List<Favorites> favorites) {
        mFavorites = favorites;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public List<Favorites> getFavorites() {
        return mFavorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        mFavorites = favorites;
    }

    public List<AddressUser> getAddress() {
        return mAddress;
    }

    public void setAddress(List<AddressUser> address) {
        mAddress = address;
    }

    public String getDateJoin() {
        return mDateJoin;
    }

    public void setDateJoin(String dateJoin) {
        mDateJoin = dateJoin;
    }

    public String getBirthDay() {
        return mBirthDay;
    }

    public void setBirthDay(String birthDay) {
        mBirthDay = birthDay;
    }

    @Override
    public String toString() {
        return "User{" +
                "mEmail='" + mEmail + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mSex='" + mSex + '\'' +
                ", mFullName='" + mFullName + '\'' +
                ", mId='" + mId + '\'' +
                ", mDateJoin='" + mDateJoin + '\'' +
                ", mFavorites=" + mFavorites +
                ", mAddress=" + mAddress +
                ", mCartItems=" + mCartItems +
                '}';
    }
}
