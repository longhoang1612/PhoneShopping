package hoanglong.thesis.graduation.juncomputer.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
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
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private String mId;
    @SerializedName("dateJoin")
    @Expose
    private String mDateJoin;
    @SerializedName("favorites")
    @Expose
    private RealmList<Favorites> mFavorites = null;
    @SerializedName("address")
    @Expose
    private RealmList<AddressUser> mAddress = null;

    public User() {
    }

    public User(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public User(String email, String password, String sex, String fullName,String dateJoin) {
        mEmail = email;
        mPassword = password;
        mSex = sex;
        mFullName = fullName;
        mDateJoin = dateJoin;
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

    public RealmList<Favorites> getFavorites() {
        return mFavorites;
    }

    public void setFavorites(RealmList<Favorites> favorites) {
        mFavorites = favorites;
    }

    public RealmList<AddressUser> getAddress() {
        return mAddress;
    }

    public void setAddress(RealmList<AddressUser> address) {
        mAddress = address;
    }

    public String getDateJoin() {
        return mDateJoin;
    }

    public void setDateJoin(String dateJoin) {
        mDateJoin = dateJoin;
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
                '}';
    }
}
