package hoanglong.thesis.graduation.juncomputer.Category.subCategory.phone;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.Category.subCategory.phone.adapter.PhoneAdapter;
import hoanglong.thesis.graduation.juncomputer.Category.subCategory.phone.adapter.PhoneCategoryAdapter;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.home.adapter.SamplePagerAdapter;
import hoanglong.thesis.graduation.juncomputer.model.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.model.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.model.PhoneCategory;
import hoanglong.thesis.graduation.juncomputer.model.PhoneProduct;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.customView.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneCategoryFragment extends Fragment implements PhoneAdapter.OnClickProductListener, PhoneCategoryAdapter.OnClickPhoneCategoryListener {

    public static final String TAG = PhoneCategoryFragment.class.getName();
    @BindView(R.id.recycler_category_phone)
    RecyclerView mRecyclerCategoryPhone;
    @BindView(R.id.viewpager)
    LoopViewPager mViewPager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    @BindView(R.id.recycler_phone_noibat)
    RecyclerView mRecyclerNoiBat;
    @BindView(R.id.progress_phone_category)
    ProgressBar mProgressPhoneCategory;
    @BindView(R.id.progress_phone_noibat)
    ProgressBar mProgressNoibat;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setDataCategory();
        setSlide();
        setDataNoiBat();
        mProgressNoibat.setVisibility(View.VISIBLE);
        mProgressPhoneCategory.setVisibility(View.VISIBLE);
    }

    private void setDataNoiBat() {
        BaseService.getService().getAllPhone().enqueue(new Callback<PhoneProduct>() {
            @Override
            public void onResponse(@NonNull Call<PhoneProduct> call, @NonNull Response<PhoneProduct> response) {
                List<ItemPhoneProduct> itemPhoneProducts = new ArrayList<>();
                mProgressNoibat.setVisibility(View.GONE);
                if (response.body() != null) {
                    for(int i =0;i<4;i++){
                        itemPhoneProducts.add(response.body().getPhoneProduct().get(i));
                    }
                    setUpRecyclerProduct(itemPhoneProducts);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhoneProduct> call, @NonNull Throwable t) {
                mProgressNoibat.setVisibility(View.GONE);
            }
        });
    }

    private void setUpRecyclerProduct(List<ItemPhoneProduct> itemPhoneProducts) {
        PhoneAdapter phoneAdapter = new PhoneAdapter(itemPhoneProducts, this);
        mRecyclerNoiBat.setAdapter(phoneAdapter);
    }

    private void setSlide() {
        mViewPager.setAdapter(new SamplePagerAdapter());
        mIndicator.setViewPager(mViewPager);
    }

    private void setDataCategory() {
        BaseService.getService().getCategoryPhone().enqueue(new Callback<PhoneCategory>() {
            @Override
            public void onResponse(@NonNull Call<PhoneCategory> call, @NonNull Response<PhoneCategory> response) {
                if (response.body() != null) {
                    mProgressPhoneCategory.setVisibility(View.GONE);
                    List<ItemPhoneCategory> phoneCategoryList = response.body().getPhoneCategoryList();
                    setUpRecyclerView(phoneCategoryList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhoneCategory> call, @NonNull Throwable t) {
                mProgressPhoneCategory.setVisibility(View.GONE);
            }
        });
    }

    private void setUpRecyclerView(List<ItemPhoneCategory> phoneCategoryList) {
        PhoneCategoryAdapter phoneCategoryAdapter = new PhoneCategoryAdapter(phoneCategoryList, this);
        mRecyclerCategoryPhone.setAdapter(phoneCategoryAdapter);
    }

    @Override
    public void onClickItemProduct(ItemPhoneProduct itemPhoneProduct) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickItem(ItemPhoneCategory phoneCategory) {
        if (getFragmentManager() != null) {
            FragmentTransactionUtils.addFragment(
                    getFragmentManager(),
                    PhoneFragment.newInstance(phoneCategory),
                    R.id.frame_home,
                    PhoneFragment.TAG,true,-1,-1);
        }
    }
}
