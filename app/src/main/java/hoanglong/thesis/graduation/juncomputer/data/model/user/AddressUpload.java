package hoanglong.thesis.graduation.juncomputer.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressUpload {
    @SerializedName("address")
    @Expose
    private List<AddressUser> mAddress = null;

    public AddressUpload(List<AddressUser> address) {
        mAddress = address;
    }

    public List<AddressUser> getAddress() {
        return mAddress;
    }

    public void setAddress(List<AddressUser> address) {
        mAddress = address;
    }

    @Override
    public String toString() {
        return "AddressUpload{" +
                "mAddress=" + mAddress +
                '}';
    }
}
