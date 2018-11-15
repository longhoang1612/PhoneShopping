package hoanglong.thesis.graduation.juncomputer.screen.phone.phone_category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.repository.PhoneRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.remote.PhoneDataSource;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.home.adapter.SamplePagerAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.PhoneAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.PhoneCategoryAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.all_phone.PhoneFragment;
import hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product.DetailProductActivity;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.customView.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;

public class PhoneCategoryFragment extends BaseFragment implements
        PhoneCategoryContract.View,
        PhoneAdapter.OnClickProductListener,
        PhoneCategoryAdapter.OnClickPhoneCategoryListener {

    public static final String TAG = PhoneCategoryFragment.class.getName();
    @BindView(R.id.recycler_category_phone)
    RecyclerView mRecyclerCategoryPhone;
    @BindView(R.id.viewpager)
    LoopViewPager mViewPager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    @BindView(R.id.recycler_phone_noibat)
    RecyclerView mRecyclerHighlight;
    @BindView(R.id.progress_phone_category)
    ProgressBar mProgressPhoneCategory;
    @BindView(R.id.progress_phone_noibat)
    ProgressBar mProgressHighlight;
    private PhoneCategoryPresenter mPresenter;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_phone_category;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mProgressHighlight.setVisibility(View.VISIBLE);
        mProgressPhoneCategory.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        setSlide();
        PhoneDataSource dataSource = PhoneDataSource.getInstance();
        PhoneRepository phoneRepository = PhoneRepository.getInstance(dataSource);
        mPresenter = new PhoneCategoryPresenter(phoneRepository);
        mPresenter.setView(this);
        loadDataCategory();
        loadDataHighLight();
    }

    private void loadDataHighLight() {
        mPresenter.getPhones();
    }

    private void loadDataCategory() {
        mPresenter.getCategories();
    }

    private void setUpRecyclerProduct(List<ItemPhoneProduct> itemPhoneProducts) {
        PhoneAdapter phoneAdapter = new PhoneAdapter(itemPhoneProducts, this);
        mRecyclerHighlight.setAdapter(phoneAdapter);
    }

    private void setSlide() {
//        mViewPager.setAdapter(new SamplePagerAdapter());
//        mIndicator.setViewPager(mViewPager);
    }

    private void setUpRecyclerView(List<ItemPhoneCategory> phoneCategoryList) {
        PhoneCategoryAdapter phoneCategoryAdapter =
                new PhoneCategoryAdapter(phoneCategoryList, this);
        mRecyclerCategoryPhone.setAdapter(phoneCategoryAdapter);
    }

    @Override
    public void onClickItemProduct(ItemPhoneProduct itemPhoneProduct) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra("BUNDLE_ITEM_PRODUCT",itemPhoneProduct);
        startActivity(intent);
    }

    @Override
    public void onClickItem(ItemPhoneCategory phoneCategory) {
        if (getFragmentManager() != null) {
            FragmentTransactionUtils.addFragment(
                    getFragmentManager(),
                    PhoneFragment.newInstance(phoneCategory),
                    R.id.frame_home,
                    PhoneFragment.TAG, true, -1, -1);
        }
    }

    @Override
    public void onGetDataSuccess(List<ItemPhoneCategory> categories) {
        if (categories == null) {
            return;
        }
        setUpRecyclerView(categories);
    }

    @Override
    public void hideProgressBar() {
        mProgressPhoneCategory.setVisibility(View.GONE);
    }

    @Override
    public void onGetDataError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetDataPhoneSuccess(List<ItemPhoneProduct> products) {
        if (products == null) {
            return;
        }
        List<ItemPhoneProduct> highlights = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            highlights.add(products.get(i));
        }
        setUpRecyclerProduct(highlights);
    }

    @Override
    public void onGetDataPhoneError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressPhone() {
        mProgressHighlight.setVisibility(View.GONE);
    }
}
