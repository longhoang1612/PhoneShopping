package hoanglong.thesis.graduation.juncomputer.data.model.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("date")
    @Expose
    private String mDate;
    @SerializedName("idProduct")
    @Expose
    private String mIdProduct;
    @SerializedName("nameProduct")
    @Expose
    private String mNameProduct;
    @SerializedName("nameUser")
    @Expose
    private String mNameUser;
    @SerializedName("imageComment")
    @Expose
    private String mImageComment;
    @SerializedName("titleComment")
    @Expose
    private String mTitleComment;
    @SerializedName("comment")
    @Expose
    private String mComment;
    @SerializedName("rating")
    @Expose
    private float mRating;

    public Comment(String date, String idProduct, String nameProduct, String nameUser, String imageComment, String titleComment, String comment, float rating) {
        mDate = date;
        mIdProduct = idProduct;
        mNameProduct = nameProduct;
        mNameUser = nameUser;
        mImageComment = imageComment;
        mTitleComment = titleComment;
        mComment = comment;
        mRating = rating;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getIdProduct() {
        return mIdProduct;
    }

    public void setIdProduct(String idProduct) {
        mIdProduct = idProduct;
    }

    public String getNameProduct() {
        return mNameProduct;
    }

    public void setNameProduct(String nameProduct) {
        mNameProduct = nameProduct;
    }

    public String getNameUser() {
        return mNameUser;
    }

    public void setNameUser(String nameUser) {
        mNameUser = nameUser;
    }

    public String getImageComment() {
        return mImageComment;
    }

    public void setImageComment(String imageComment) {
        mImageComment = imageComment;
    }

    public String getTitleComment() {
        return mTitleComment;
    }

    public void setTitleComment(String titleComment) {
        mTitleComment = titleComment;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "mDate='" + mDate + '\'' +
                ", mIdProduct='" + mIdProduct + '\'' +
                ", mNameProduct='" + mNameProduct + '\'' +
                ", mNameUser='" + mNameUser + '\'' +
                ", mImageComment='" + mImageComment + '\'' +
                ", mTitleComment='" + mTitleComment + '\'' +
                ", mComment='" + mComment + '\'' +
                ", mRating=" + mRating +
                '}';
    }
}