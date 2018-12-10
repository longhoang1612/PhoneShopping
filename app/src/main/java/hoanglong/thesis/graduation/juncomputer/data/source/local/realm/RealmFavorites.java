package hoanglong.thesis.graduation.juncomputer.data.source.local.realm;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.user.Favorites;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmFavorites {
    private static Realm realm = Realm.getDefaultInstance();
    private static final String PRIMARY_KEY_FAVORITES = "mTitleFav";

    public static void favorites(Favorites favorites) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(favorites);
        realm.commitTransaction();
    }

    public static void unFavorites(String titleFavorites) {
        RealmResults<Favorites> listFavorites = realm.where(Favorites.class).equalTo(PRIMARY_KEY_FAVORITES, titleFavorites).findAll();
        realm.beginTransaction();
        listFavorites.deleteFromRealm(0);
        realm.commitTransaction();
    }

    public static Favorites getItemFavorites(String titleFavorites) {
        RealmObject realmObject = realm.where(Favorites.class).equalTo(PRIMARY_KEY_FAVORITES, titleFavorites).findFirst();
        return (Favorites) realmObject;
    }

    public static List<Favorites> getFavorites() {
        return realm.where(Favorites.class).findAll();
    }

    public static RealmList<Favorites> getPlaylist(){
        realm.beginTransaction();
        RealmList<Favorites> realmResults = new RealmList<>();
        realmResults.addAll(realm.where(Favorites.class).findAll());
        realm.commitTransaction();
        return realmResults;
    }

}
