package hoanglong.thesis.graduation.juncomputer.data.model.detailPhone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListExtraProduct {
    @SerializedName("imageExtra")
    @Expose
    private String imageExtra;
    @SerializedName("titleExtra")
    @Expose
    private String titleExtra;
    @SerializedName("_id")
    @Expose
    private String id;

    public ListExtraProduct(String imageExtra, String titleExtra, String id) {
        this.imageExtra = imageExtra;
        this.titleExtra = titleExtra;
        this.id = id;
    }

    public String getImageExtra() {
        return imageExtra;
    }

    public void setImageExtra(String imageExtra) {
        this.imageExtra = imageExtra;
    }

    public String getTitleExtra() {
        return titleExtra;
    }

    public void setTitleExtra(String titleExtra) {
        this.titleExtra = titleExtra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ListExtraProduct{" +
                "imageExtra='" + imageExtra + '\'' +
                ", titleExtra='" + titleExtra + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
