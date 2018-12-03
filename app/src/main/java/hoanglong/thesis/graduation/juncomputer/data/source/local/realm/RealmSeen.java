package hoanglong.thesis.graduation.juncomputer.data.source.local.realm;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemSeen;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmSeen {
    private static Realm realm = Realm.getDefaultInstance();
    private static final String PRIMARY_KEY_CART = "id";

    public static void addSeenItem(ItemSeen itemPhoneProduct) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(itemPhoneProduct);
        realm.commitTransaction();
    }

//    public static void removeInCart(CartItem cartItem) {
//        RealmObject realmObject = realm.where(CartItem.class).equalTo(PRIMARY_KEY_CART, cartItem.getId()).findFirst();
//        realm.beginTransaction();
//        if (realmObject == null) {
//            realm.copyToRealmOrUpdate(cartItem);
//        } else {
//            ((CartItem) realmObject).setQuantity(((CartItem) realmObject).getQuantity() - 1);
//        }
//        realm.commitTransaction();
//    }
//
//    public static CartItem getTrack(CartItem cartItem) {
//        RealmObject realmObject = realm.where(CartItem.class).equalTo(PRIMARY_KEY_CART, cartItem.getId()).findFirst();
//        return (CartItem) realmObject;
//    }
//
//    public static void deleteItemFromCart(CartItem cartItem) {
//        RealmResults<CartItem> carts = realm.where(CartItem.class).equalTo(PRIMARY_KEY_CART, cartItem.getId()).findAll();
//        realm.beginTransaction();
//        carts.deleteFromRealm(0);
//        realm.commitTransaction();
//    }

    public static List<ItemSeen> getListScreen() {
        return realm.where(ItemSeen.class).findAll();
    }
}
