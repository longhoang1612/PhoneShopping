package hoanglong.thesis.graduation.juncomputer.data.model;

public class Category {
    private String mImage;
    private String mTitle;
    private String mType;

    public Category(String image, String title, String type) {
        mImage = image;
        mTitle = title;
        mType = type;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
