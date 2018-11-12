package hoanglong.thesis.graduation.juncomputer.data.model.detailPhone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailPhone {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sale")
    @Expose
    private String sale;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("titleH2")
    @Expose
    private String titleH2;
    @SerializedName("titleContent")
    @Expose
    private String titleContent;
    @SerializedName("linkVideo")
    @Expose
    private String linkVideo;
    @SerializedName("topContentP")
    @Expose
    private String topContentP;
    @SerializedName("slider")
    @Expose
    private List<String> slider = null;
    @SerializedName("detailContent")
    @Expose
    private List<DetailContent> detailContent = null;
    @SerializedName("listParameter")
    @Expose
    private List<ListParameter> listParameter = null;
    @SerializedName("listExtraProduct")
    @Expose
    private List<ListExtraProduct> listExtraProduct = null;
    @SerializedName("listSale")
    @Expose
    private List<String> listSale = null;

    public DetailPhone() {
    }

    public DetailPhone(String id, String title, String sale, String price, String titleH2, String titleContent, String linkVideo, String topContentP, List<String> slider, List<DetailContent> detailContent, List<ListParameter> listParameter, List<ListExtraProduct> listExtraProduct, List<String> listSale) {
        this.id = id;
        this.title = title;
        this.sale = sale;
        this.price = price;
        this.titleH2 = titleH2;
        this.titleContent = titleContent;
        this.linkVideo = linkVideo;
        this.topContentP = topContentP;
        this.slider = slider;
        this.detailContent = detailContent;
        this.listParameter = listParameter;
        this.listExtraProduct = listExtraProduct;
        this.listSale = listSale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitleH2() {
        return titleH2;
    }

    public void setTitleH2(String titleH2) {
        this.titleH2 = titleH2;
    }

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getTopContentP() {
        return topContentP;
    }

    public void setTopContentP(String topContentP) {
        this.topContentP = topContentP;
    }

    public List<String> getSlider() {
        return slider;
    }

    public void setSlider(List<String> slider) {
        this.slider = slider;
    }

    public List<DetailContent> getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(List<DetailContent> detailContent) {
        this.detailContent = detailContent;
    }

    public List<ListParameter> getListParameter() {
        return listParameter;
    }

    public void setListParameter(List<ListParameter> listParameter) {
        this.listParameter = listParameter;
    }

    public List<ListExtraProduct> getListExtraProduct() {
        return listExtraProduct;
    }

    public void setListExtraProduct(List<ListExtraProduct> listExtraProduct) {
        this.listExtraProduct = listExtraProduct;
    }

    public List<String> getListSale() {
        return listSale;
    }

    public void setListSale(List<String> listSale) {
        this.listSale = listSale;
    }

    @Override
    public String toString() {
        return "DetailPhone{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", sale='" + sale + '\'' +
                ", price='" + price + '\'' +
                ", titleH2='" + titleH2 + '\'' +
                ", titleContent='" + titleContent + '\'' +
                ", linkVideo='" + linkVideo + '\'' +
                ", topContentP='" + topContentP + '\'' +
                ", slider=" + slider +
                ", detailContent=" + detailContent +
                ", listParameter=" + listParameter +
                ", listExtraProduct=" + listExtraProduct +
                ", listSale=" + listSale +
                '}';
    }
}
