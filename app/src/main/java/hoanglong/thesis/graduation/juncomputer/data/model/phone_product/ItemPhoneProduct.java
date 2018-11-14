package hoanglong.thesis.graduation.juncomputer.data.model.phone_product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ItemPhoneProduct implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("deal")
    @Expose
    private String deal;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("numberRating")
    @Expose
    private String numberRating;
    @SerializedName("titleH2")
    @Expose
    private String titleH2;
    @SerializedName("titleContent")
    @Expose
    private String titleContent;
    @SerializedName("__v")
    @Expose
    private Integer v;
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
    @SerializedName("linkVideo")
    @Expose
    private String linkVideo;

    protected ItemPhoneProduct(Parcel in) {
        id = in.readString();
        type = in.readString();
        title = in.readString();
        price = in.readString();
        deal = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readInt();
        }
        numberRating = in.readString();
        titleH2 = in.readString();
        titleContent = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
        slider = in.createStringArrayList();
        listSale = in.createStringArrayList();
        linkVideo = in.readString();
    }

    public static final Creator<ItemPhoneProduct> CREATOR = new Creator<ItemPhoneProduct>() {
        @Override
        public ItemPhoneProduct createFromParcel(Parcel in) {
            return new ItemPhoneProduct(in);
        }

        @Override
        public ItemPhoneProduct[] newArray(int size) {
            return new ItemPhoneProduct[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getNumberRating() {
        return numberRating;
    }

    public void setNumberRating(String numberRating) {
        this.numberRating = numberRating;
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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
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

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeString(deal);
        dest.writeString(image);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rating);
        }
        dest.writeString(numberRating);
        dest.writeString(titleH2);
        dest.writeString(titleContent);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
        dest.writeStringList(slider);
        dest.writeStringList(listSale);
        dest.writeString(linkVideo);
    }
}
