package hoanglong.thesis.graduation.juncomputer.model.DetailPhone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailContent {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("image")
    private String image;

    public DetailContent() {
    }

    public DetailContent(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DetailContent{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
