package hoanglong.thesis.graduation.juncomputer.data.source.local.realm;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUser;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmAddress {
    private static Realm realm = Realm.getDefaultInstance();
    private static final String PRIMARY_KEY_ADDRESS = "mId";

    public static void addAddress(AddressUser addressUser) {
        RealmObject realmObject = realm.where(AddressUser.class).equalTo(PRIMARY_KEY_ADDRESS, addressUser.getId()).findFirst();
        realm.beginTransaction();
        if (realmObject == null) {
            realm.copyToRealmOrUpdate(addressUser);
        } else {
            ((AddressUser) realmObject).setAddressOrder(addressUser.getAddressOrder());
        }
        realm.commitTransaction();
    }

    public static void updateAddress(AddressUser addressUser) {
        RealmObject realmObject = realm.where(AddressUser.class).equalTo(PRIMARY_KEY_ADDRESS, addressUser.getId()).findFirst();
        realm.beginTransaction();
        if (realmObject != null) {
            ((AddressUser) realmObject).setAddressOrder(addressUser.getAddressOrder());
            ((AddressUser) realmObject).setPhoneNumber(addressUser.getPhoneNumber());
            ((AddressUser) realmObject).setUserNameOrder(addressUser.getUserNameOrder());
        }
        realm.commitTransaction();
    }

    public static void deleteAddress(int id) {
        RealmResults<AddressUser> listAddress = realm.where(AddressUser.class).equalTo(PRIMARY_KEY_ADDRESS, id).findAll();
        realm.beginTransaction();
        listAddress.deleteFromRealm(0);
        realm.commitTransaction();
    }

    public static AddressUser getItemFavorites(int id) {
        RealmObject realmObject = realm.where(AddressUser.class).equalTo(PRIMARY_KEY_ADDRESS, id).findFirst();
        return (AddressUser) realmObject;
    }

    public static List<AddressUser> getListAddress() {
        return realm.where(AddressUser.class).findAll();
    }
}
