package hoanglong.thesis.graduation.juncomputer.screen.phone.all_phone;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.AllPhoneAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product.DetailProductActivity;
import hoanglong.thesis.graduation.juncomputer.utils.Utils;

public class AllPhoneFragment extends BaseFragment implements AllPhoneAdapter.OnClickProductListener {

    public static final String TAG = AllPhoneFragment.class.getName();
    public static final String BUNDLE_ALL_PRODUCT = "BUNDLE_ALL_PRODUCT";
    private List<ItemPhoneProduct> mPhoneProducts;
    @BindView(R.id.recycler_phone)
    RecyclerView mRecyclerPhone;
    @BindView(R.id.text_result)
    TextView mTextResult;
    @BindView(R.id.progress_phone)
    ProgressBar mProgressBar;
    @BindView(R.id.ic_grid)
    ImageView mImageGrid;
    private boolean mIsViewWithCatalog = true;
    private AllPhoneAdapter mAllPhoneAdapter;

    public static AllPhoneFragment newInstance(List<ItemPhoneProduct> phoneProducts) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_ALL_PRODUCT,
                (ArrayList<? extends Parcelable>) phoneProducts);
        AllPhoneFragment fragment = new AllPhoneFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPhoneProducts = bundle.getParcelableArrayList(BUNDLE_ALL_PRODUCT);
        }
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_all_phone;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        if(mIsViewWithCatalog){
            mImageGrid.setImageResource(R.drawable.ic_format_list_bulleted_black_24dp);
        }else{
            mImageGrid.setImageResource(R.drawable.ic_view_module_black_24dp);
        }
        mImageGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsViewWithCatalog){
                    mImageGrid.setImageResource(R.drawable.ic_view_module_black_24dp);
                }else{
                    mImageGrid.setImageResource(R.drawable.ic_format_list_bulleted_black_24dp);
                }
                mIsViewWithCatalog = !mIsViewWithCatalog;
                mRecyclerPhone.setLayoutManager(mIsViewWithCatalog ? new LinearLayoutManager(getContext()) : new GridLayoutManager(getContext(), 2));
                mRecyclerPhone.setAdapter(mAllPhoneAdapter);
            }
        });
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mProgressBar.setVisibility(View.GONE);
        mTextResult.setText(String.format(getString(R.string.finded_product), mPhoneProducts.size()));
        mAllPhoneAdapter = new AllPhoneAdapter(mIsViewWithCatalog, mPhoneProducts, this);
        mRecyclerPhone.setLayoutManager(mIsViewWithCatalog ? new LinearLayoutManager(getContext()) : new GridLayoutManager(getContext(), 2));
        mRecyclerPhone.setAdapter(mAllPhoneAdapter);
    }

    @Override
    public void onClickItemProduct(ItemPhoneProduct itemPhoneProduct) {
        Utils.addSeen(itemPhoneProduct);
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra("BUNDLE_ITEM_PRODUCT", itemPhoneProduct.getTitle());
        startActivity(intent);
    }
}
