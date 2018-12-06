package hoanglong.thesis.graduation.juncomputer.data.source.local.realm;

import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmUser {

    private static Realm realm = Realm.getDefaultInstance();
    private static final String PRIMARY_KEY_USER = "mId";

    public static void saveUser(User user) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    public static void signOut(User user) {
        RealmResults<User> users = realm.where(User.class).equalTo(PRIMARY_KEY_USER, user.getId()).findAll();
        realm.beginTransaction();
        users.deleteFromRealm(0);
        realm.commitTransaction();
    }

    public static User getUser() {
        return realm.where(User.class).findFirst();
    }

}
