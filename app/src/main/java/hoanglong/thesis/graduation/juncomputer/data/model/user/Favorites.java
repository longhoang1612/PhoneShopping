package hoanglong.thesis.graduation.juncomputer.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Favorites extends RealmObject {

    @PrimaryKey
    @SerializedName("titleFav")
    @Expose
    private String mTitleFav;
    @SerializedName("imageFav")
    @Expose
    private String mImageFav;
    @SerializedName("priceFav")
    @Expose
    private String mPriceFav;
    @SerializedName("ratingFav")
    @Expose
    private int mRatingFav;
    @SerializedName("countRatingFav")
    @Expose
    private String mCountRatingFav;

    public Favorites() {
    }

    public String getTitleFav() {
        return mTitleFav;
    }

    public void setTitleFav(String titleFav) {
        mTitleFav = titleFav;
    }

    public String getImageFav() {
        return mImageFav;
    }

    public void setImageFav(String imageFav) {
        mImageFav = imageFav;
    }

    public String getPriceFav() {
        return mPriceFav;
    }

    public void setPriceFav(String priceFav) {
        mPriceFav = priceFav;
    }

    public int getRatingFav() {
        return mRatingFav;
    }

    public void setRatingFav(int ratingFav) {
        mRatingFav = ratingFav;
    }

    public String getCountRatingFav() {
        return mCountRatingFav;
    }

    public void setCountRatingFav(String countRatingFav) {
        mCountRatingFav = countRatingFav;
    }

    public Favorites(String titleFav, String imageFav, String priceFav, int ratingFav, String countRatingFav) {
        mTitleFav = titleFav;
        mImageFav = imageFav;
        mPriceFav = priceFav;
        mRatingFav = ratingFav;
        mCountRatingFav = countRatingFav;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "mTitleFav='" + mTitleFav + '\'' +
                ", mImageFav='" + mImageFav + '\'' +
                ", mPriceFav='" + mPriceFav + '\'' +
                ", mRatingFav=" + mRatingFav +
                ", mCountRatingFav='" + mCountRatingFav + '\'' +
                '}';
    }
}
