package hoanglong.thesis.graduation.juncomputer.data.model.detailPhone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllDetailPhone {
    @SerializedName("DetailPhone")
    @Expose
    private List<DetailPhone> detailPhone = null;

    public AllDetailPhone(List<DetailPhone> detailPhone) {
        this.detailPhone = detailPhone;
    }

    public List<DetailPhone> getDetailPhone() {
        return detailPhone;
    }

    public void setDetailPhone(List<DetailPhone> detailPhone) {
        this.detailPhone = detailPhone;
    }
}
