package hoanglong.thesis.graduation.juncomputer.model.DetailPhone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListParameter {
    @SerializedName("titlePara")
    @Expose
    private String titlePara;
    @SerializedName("contentPara")
    @Expose
    private String contentPara;
    @SerializedName("_id")
    @Expose
    private String id;

    public ListParameter(String titlePara, String contentPara, String id) {
        this.titlePara = titlePara;
        this.contentPara = contentPara;
        this.id = id;
    }

    public String getTitlePara() {
        return titlePara;
    }

    public void setTitlePara(String titlePara) {
        this.titlePara = titlePara;
    }

    public String getContentPara() {
        return contentPara;
    }

    public void setContentPara(String contentPara) {
        this.contentPara = contentPara;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ListParameter{" +
                "titlePara='" + titlePara + '\'' +
                ", contentPara='" + contentPara + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
