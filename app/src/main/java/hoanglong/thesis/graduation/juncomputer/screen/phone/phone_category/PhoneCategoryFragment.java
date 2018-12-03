package hoanglong.thesis.graduation.juncomputer.screen.phone.phone_category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemSeen;
import hoanglong.thesis.graduation.juncomputer.data.repository.PhoneRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmSeen;
import hoanglong.thesis.graduation.juncomputer.data.source.remote.PhoneDataSource;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.AccessoriesCategoryAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.PhoneAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.PhoneCategoryAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.PhoneSeenAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.all_phone.AllPhoneFragment;
import hoanglong.thesis.graduation.juncomputer.screen.phone.all_phone.PhoneFragment;
import hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product.DetailProductActivity;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;

public class PhoneCategoryFragment extends BaseFragment implements
        PhoneCategoryContract.View,
        PhoneAdapter.OnClickProductListener,
        PhoneCategoryAdapter.OnClickPhoneCategoryListener,
        View.OnClickListener, AccessoriesCategoryAdapter.OnClickPhoneCategoryListener,
        PhoneSeenAdapter.OnClickProductListener {

    public static final String TAG = PhoneCategoryFragment.class.getName();
    public static final String BUNDLE_CATEGORY = "BUNDLE_CATEGORY";

    @BindView(R.id.recycler_category_phone)
    RecyclerView mRecyclerCategoryPhone;
    @BindView(R.id.recycler_phone_noibat)
    RecyclerView mRecyclerHighlight;
    @BindView(R.id.progress_phone_category)
    ProgressBar mProgressPhoneCategory;
    @BindView(R.id.progress_phone_noibat)
    ProgressBar mProgressHighlight;
    @BindView(R.id.button_see_more)
    Button mButtonSeeMore;
    @BindView(R.id.card_no_item)
    CardView mCardNoItem;
    @BindView(R.id.recycler_phone_recent)
    RecyclerView mRecyclerPhoneRecent;
    private PhoneCategoryPresenter mPresenter;
    private Category mCategory;
    private List<ItemPhoneProduct> mPhoneProducts;

    public static PhoneCategoryFragment newInstance(Category category) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_CATEGORY, category);
        PhoneCategoryFragment fragment = new PhoneCategoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        mCategory = bundle.getParcelable(BUNDLE_CATEGORY);
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_phone_category;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mProgressHighlight.setVisibility(View.VISIBLE);
        mProgressPhoneCategory.setVisibility(View.VISIBLE);
        mButtonSeeMore.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mPhoneProducts = new ArrayList<>();
        PhoneDataSource dataSource = PhoneDataSource.getInstance();
        PhoneRepository phoneRepository = PhoneRepository.getInstance(dataSource);
        mPresenter = new PhoneCategoryPresenter(phoneRepository);
        mPresenter.setView(this);
        loadDataCategory();
        loadDataHighLight();
        loadDataSeen();
    }

    private void loadDataSeen() {
        if (RealmSeen.getListScreen() == null || RealmSeen.getListScreen().size() == 0) {
            mRecyclerPhoneRecent.setVisibility(View.GONE);
            mCardNoItem.setVisibility(View.VISIBLE);
        } else {
            mRecyclerPhoneRecent.setVisibility(View.VISIBLE);
            mCardNoItem.setVisibility(View.GONE);
            List<ItemSeen> itemSeens = new ArrayList<>();
            for (ItemSeen itemSeen : RealmSeen.getListScreen()) {
                if (itemSeen.getTypeCategory().equals(mCategory.getType())) {
                    itemSeens.add(itemSeen);
                }
            }
            if(itemSeens.size()==0){
                mRecyclerPhoneRecent.setVisibility(View.GONE);
                mCardNoItem.setVisibility(View.VISIBLE);
            }
            PhoneSeenAdapter phoneAdapter = new PhoneSeenAdapter(itemSeens, this);
            mRecyclerPhoneRecent.setAdapter(phoneAdapter);
        }
    }

    private void loadDataHighLight() {
        mPresenter.getPhones(mCategory.getType());
    }

    private void loadDataCategory() {
        mPresenter.getCategories(mCategory.getType());
    }

    private void setUpRecyclerProduct(List<ItemPhoneProduct> itemPhoneProducts) {
        PhoneAdapter phoneAdapter = new PhoneAdapter(itemPhoneProducts, this);
        mRecyclerHighlight.setAdapter(phoneAdapter);
    }

    private void setUpRecyclerView(List<ItemPhoneCategory> phoneCategoryList) {
        if (mCategory.getType().equals(Constant.Category.accessories_type)) {
            AccessoriesCategoryAdapter accessoriesCategoryAdapter =
                    new AccessoriesCategoryAdapter(phoneCategoryList, this);
            mRecyclerCategoryPhone.setAdapter(accessoriesCategoryAdapter);
        } else {
            PhoneCategoryAdapter phoneCategoryAdapter =
                    new PhoneCategoryAdapter(phoneCategoryList, this);
            mRecyclerCategoryPhone.setAdapter(phoneCategoryAdapter);
        }
    }

    @Override
    public void onClickItemProduct(ItemPhoneProduct itemPhoneProduct) {
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

        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra("BUNDLE_ITEM_PRODUCT", itemPhoneProduct.getTitle());
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
        mPhoneProducts = products;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_see_more:
                if (getFragmentManager() != null) {
                    FragmentTransactionUtils.addFragment(
                            getFragmentManager(),
                            AllPhoneFragment.newInstance(mPhoneProducts),
                            R.id.frame_home,
                            PhoneFragment.TAG, true, -1, -1);
                }
                break;
        }
    }

    @Override
    public void onClickItemSeenProduct(ItemSeen itemPhoneProduct) {
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

        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra("BUNDLE_ITEM_PRODUCT", itemPhoneProduct.getTitle());
        startActivity(intent);
    }
}
