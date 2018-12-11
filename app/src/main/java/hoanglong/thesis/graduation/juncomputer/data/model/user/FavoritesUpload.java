package hoanglong.thesis.graduation.juncomputer.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoritesUpload {

    @SerializedName("favorites")
    @Expose
    private List<Favorites> mFavorites = null;

    public FavoritesUpload(List<Favorites> favorites) {
        mFavorites = favorites;
    }

    public List<Favorites> getFavorites() {
        return mFavorites;
    }

    public void setFavorites(List<Favorites> favorites) {
        mFavorites = favorites;
    }
}
