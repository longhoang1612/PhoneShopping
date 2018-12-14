package hoanglong.thesis.graduation.juncomputer.data;

import android.support.annotation.StringDef;

import static hoanglong.thesis.graduation.juncomputer.data.TypeConfirmOrder.ORDER_CONFIRMED;
import static hoanglong.thesis.graduation.juncomputer.data.TypeConfirmOrder.ORDER_DELIVERY;
import static hoanglong.thesis.graduation.juncomputer.data.TypeConfirmOrder.ORDER_DONE;
import static hoanglong.thesis.graduation.juncomputer.data.TypeConfirmOrder.ORDER_WAITING_CONFIRM;

@StringDef({ORDER_DELIVERY, ORDER_WAITING_CONFIRM, ORDER_CONFIRMED,ORDER_DONE})
public @interface TypeConfirmOrder {
    String ORDER_DELIVERY = "Đang giao hàng";
    String ORDER_WAITING_CONFIRM = "Đang xác nhận";
    String ORDER_CONFIRMED = "Đã xác nhận đơn hàng";
    String ORDER_DONE = "Giao hàng thành công";
}
