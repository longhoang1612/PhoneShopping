package hoanglong.thesis.graduation.juncomputer.data.source.local.realm;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmCart {
    private static Realm realm = Realm.getDefaultInstance();
    private static final String PRIMARY_KEY_CART = "mId";

    public static void addToCart(CartItem cartItem) {
        RealmObject realmObject = realm.where(CartItem.class).equalTo(PRIMARY_KEY_CART, cartItem.getId()).findFirst();
        realm.beginTransaction();
        if (realmObject == null) {
            realm.copyToRealmOrUpdate(cartItem);
        } else {
            ((CartItem) realmObject).setQuantity(((CartItem) realmObject).getQuantity() + 1);
        }
        realm.commitTransaction();
    }

    public static void removeInCart(CartItem cartItem) {
        RealmObject realmObject = realm.where(CartItem.class).equalTo(PRIMARY_KEY_CART, cartItem.getId()).findFirst();
        realm.beginTransaction();
        if (realmObject == null) {
            realm.copyToRealmOrUpdate(cartItem);
        } else {
            ((CartItem) realmObject).setQuantity(((CartItem) realmObject).getQuantity() - 1);
        }
        realm.commitTransaction();
    }

    public static CartItem getTrack(CartItem cartItem) {
        RealmObject realmObject = realm.where(CartItem.class).equalTo(PRIMARY_KEY_CART, cartItem.getId()).findFirst();
        return (CartItem) realmObject;
    }

    public static void deleteItemFromCart(CartItem cartItem) {
        RealmResults<CartItem> carts = realm.where(CartItem.class).equalTo(PRIMARY_KEY_CART, cartItem.getId()).findAll();
        realm.beginTransaction();
        carts.deleteFromRealm(0);
        realm.commitTransaction();
    }

    public static void deleteAll() {
        RealmResults<CartItem> carts = realm.where(CartItem.class).findAll();
        realm.beginTransaction();
        carts.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static void addAll() {
        RealmResults<CartItem> carts = realm.where(CartItem.class).findAll();
        realm.beginTransaction();
        carts.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static List<CartItem> getCartOffline() {
        return realm.where(CartItem.class).findAll();
    }
}
