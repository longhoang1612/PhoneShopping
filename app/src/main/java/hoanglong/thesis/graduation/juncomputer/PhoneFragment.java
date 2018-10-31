package hoanglong.thesis.graduation.juncomputer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.Category.subCategory.phone.adapter.PhoneAdapter;
import hoanglong.thesis.graduation.juncomputer.model.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.model.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.model.PhoneProduct;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneFragment extends Fragment implements PhoneAdapter.OnClickProductListener {

    public static final String TAG = PhoneFragment.class.getName();
    private static final String BUNDLE_TYPE_PHONE = "BUNDLE_TYPE_PHONE";
    private String typePhone;
    private TextView mTextType;
    private RecyclerView mRecyclerPhone;
    private TextView mTextResult;

    public static PhoneFragment newInstance(ItemPhoneCategory itemPhoneCategory) {
        Bundle args = new Bundle();
        PhoneFragment fragment = new PhoneFragment();
        args.putString(BUNDLE_TYPE_PHONE, itemPhoneCategory.getType());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typePhone = getArguments().getString(BUNDLE_TYPE_PHONE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextType = view.findViewById(R.id.text_type);
        mTextType.setText(typePhone);
        mRecyclerPhone = view.findViewById(R.id.recycler_phone);
        mTextResult = view.findViewById(R.id.text_result);
        setUpData();
    }

    private void setUpData() {
        BaseService.getService().getPhoneProduct(typePhone).enqueue(new Callback<PhoneProduct>() {
            @Override
            public void onResponse(@NonNull Call<PhoneProduct> call, @NonNull Response<PhoneProduct> response) {
                List<ItemPhoneProduct> productList = null;
                if(response.body()!=null){
                    productList = response.body().getPhoneProduct();
                    setUpRecyclerView(productList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhoneProduct> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void setUpRecyclerView(List<ItemPhoneProduct> productList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tìm thấy ").append(productList.size()).append(" sản phẩm");
        mTextResult.setText(stringBuilder.toString());
        PhoneAdapter phoneAdapter = new PhoneAdapter(productList,this);
        mRecyclerPhone.setAdapter(phoneAdapter);
    }

    @Override
    public void onClickItemProduct(ItemPhoneProduct itemPhoneProduct) {

    }
}