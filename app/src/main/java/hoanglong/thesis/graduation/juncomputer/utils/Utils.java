package hoanglong.thesis.graduation.juncomputer.utils;

import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemSeen;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmSeen;

public class Utils {
    public static void addSeen(ItemPhoneProduct itemPhoneProduct){
        ItemSeen itemSeen = new ItemSeen(itemPhoneProduct.getId(),
                itemPhoneProduct.getType(),
                itemPhoneProduct.getTitle(),
                itemPhoneProduct.getTypeCategory(),
                itemPhoneProduct.getPrice(),
                itemPhoneProduct.getDeal(),
                itemPhoneProduct.getImage(),
                itemPhoneProduct.getRating(),
                itemPhoneProduct.getNumberRating());

        RealmSeen.addSeenItem(itemSeen);
    }
}
