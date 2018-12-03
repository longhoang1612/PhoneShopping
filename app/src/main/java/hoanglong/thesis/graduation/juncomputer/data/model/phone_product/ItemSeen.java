package hoanglong.thesis.graduation.juncomputer.data.model.phone_product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ItemSeen extends RealmObject{

    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("typeCategory")
    @Expose
    private String typeCategory;
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

    public ItemSeen() {
    }

    public ItemSeen(String id, String type, String title, String typeCategory, String price, String deal, String image, Integer rating, String numberRating) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.typeCategory = typeCategory;
        this.price = price;
        this.deal = deal;
        this.image = image;
        this.rating = rating;
        this.numberRating = numberRating;
    }

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

    public String getTypeCategory() {
        return typeCategory;
    }

    public void setTypeCategory(String typeCategory) {
        this.typeCategory = typeCategory;
    }
}
