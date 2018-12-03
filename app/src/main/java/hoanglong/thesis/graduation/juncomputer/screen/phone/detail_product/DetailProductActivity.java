package hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.DetailContent;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ListParameter;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.PhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.screen.bottomsheet.AddCartBottomDialogFragment;
import hoanglong.thesis.graduation.juncomputer.screen.home.HomeActivity;
import hoanglong.thesis.graduation.juncomputer.screen.home.UpdateCart;
import hoanglong.thesis.graduation.juncomputer.screen.home.adapter.SamplePagerAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.ContentAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.ExtraProductAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.InfoAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.SaleAdapter;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.customView.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailProductActivity extends AppCompatActivity
        implements View.OnClickListener, SamplePagerAdapter.ClickSliderListener, Listener {

    private static final String TAG = DetailProductActivity.class.getName();
    @BindView(R.id.text_sale)
    TextView mTextSale;
    @BindView(R.id.text_title_product)
    TextView mTextNameProduct;
    @BindView(R.id.text_price_product)
    TextView mTextPrice;
    @BindView(R.id.rating_bar_product)
    RatingBar mRatingBar;
    @BindView(R.id.text_number_rating_product)
    TextView mTextNumRating;
    @BindView(R.id.recycler_sale)
    RecyclerView mRecyclerSale;
    @BindView(R.id.recycler_extra_product)
    RecyclerView mRecyclerExtraProduct;
    @BindView(R.id.recycler_info_product)
    RecyclerView mRecyclerInfoProduct;
    @BindView(R.id.text_title_content)
    TextView mTextTitleContent;
    @BindView(R.id.text_h2)
    TextView mTextH2;
    @BindView(R.id.recycler_des)
    RecyclerView mRecyclerDes;
    @BindView(R.id.viewpager)
    LoopViewPager mViewPager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    @BindView(R.id.relative_all_content)
    RelativeLayout mRLAllContent;
    @BindView(R.id.text_see_detail)
    TextView textSeeDetail;
    @BindView(R.id.relative_comment)
    RelativeLayout mRelativeComment;
    @BindView(R.id.fab_cart)
    FloatingActionButton mFABCart;
    @BindView(R.id.tv_number_cart)
    TextView mTextNumberCart;
//    @BindView(R.id.ic_shopping)
//    ImageView mImageShopping;

    private ItemPhoneProduct itemPhoneProduct;
    private List<DetailContent> mContentListHide;
    private List<ListParameter> mInfoProducts;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);
        mRLAllContent.setOnClickListener(this);
        textSeeDetail.setOnClickListener(this);
        mRelativeComment.setOnClickListener(this);
        mFABCart.setOnClickListener(this);
        mContentListHide = new ArrayList<>();
        mInfoProducts = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("BUNDLE_ITEM_PRODUCT");
        }
        setData();
    }

    private void setData() {
        final Call<PhoneProduct> productCall = BaseService.getService().getPhoneWithTitle(title);
        productCall.enqueue(new Callback<PhoneProduct>() {
            @Override
            public void onResponse(@NonNull Call<PhoneProduct> call, @NonNull Response<PhoneProduct> response) {
                if (response.body() != null) {
                    itemPhoneProduct = response.body().getPhoneProduct().get(0);
                    setupView(itemPhoneProduct);
                    setSlide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhoneProduct> call, @NonNull Throwable t) {

            }
        });
    }

    private void setSlide() {
        SamplePagerAdapter samplePagerAdapter = new SamplePagerAdapter(
                itemPhoneProduct.getSlider(), this
        );
        mViewPager.setAdapter(samplePagerAdapter);
        mIndicator.setViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mImageShopping.setVisibility(View.VISIBLE);
        onUpdateCart();
    }

    private void onUpdateCart() {
        if (RealmCart.getCartOffline() == null) {
            return;
        }
        mTextNumberCart.setText(String.valueOf(RealmCart.getCartOffline().size()));
    }

    private void setupView(ItemPhoneProduct itemPhoneProduct) {
        if (itemPhoneProduct == null) {
            return;
        }
        mTextSale.setText(itemPhoneProduct.getDeal());
        mTextNameProduct.setText(itemPhoneProduct.getTitle());
        mTextPrice.setText(itemPhoneProduct.getPrice());
        mRecyclerSale.setAdapter(
                new SaleAdapter(itemPhoneProduct.getListSale()));
        mRecyclerSale.setNestedScrollingEnabled(false);
        mRecyclerExtraProduct.setAdapter(
                new ExtraProductAdapter(itemPhoneProduct.getListExtraProduct()));
        mRecyclerExtraProduct.setNestedScrollingEnabled(false);

        //InFo
        if (itemPhoneProduct.getListParameter().size() > 3) {
            for (int i = 0; i < 3; i++) {
                mInfoProducts.add(itemPhoneProduct.getListParameter().get(i));
            }
        } else {
            mInfoProducts.addAll(itemPhoneProduct.getListParameter());
        }
        mRecyclerInfoProduct.setAdapter(
                new InfoAdapter(mInfoProducts));
        mRecyclerInfoProduct.setNestedScrollingEnabled(false);

        //Content
        mTextTitleContent.setText(itemPhoneProduct.getTitleContent());
        mTextH2.setText(itemPhoneProduct.getTitleH2());
        if (itemPhoneProduct.getDetailContent().size() >= 2) {
            for (int i = 0; i < 2; i++) {
                mContentListHide.add(itemPhoneProduct.getDetailContent().get(i));
            }
        } else {
            mContentListHide.addAll(itemPhoneProduct.getDetailContent());
        }

        mRecyclerDes.setAdapter(
                new ContentAdapter(mContentListHide));
        mRecyclerDes.setNestedScrollingEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_all_content:
                Intent intent = new Intent(this, ContentDetailActivity.class);
                intent.putExtra(ContentDetailActivity.BUNDLE_TITLE_CONTENT, itemPhoneProduct.getTitleContent());
                intent.putExtra(ContentDetailActivity.BUNDLE_H2_CONTENT, itemPhoneProduct.getTitleH2());
                intent.putParcelableArrayListExtra(ContentDetailActivity.BUNDLE_DETAIL_CONTENT,
                        (ArrayList<? extends Parcelable>) itemPhoneProduct.getDetailContent());
                startActivity(intent);
                break;
            case R.id.text_see_detail:
                Intent intentInfo = new Intent(this, InfoDetailActivity.class);
                intentInfo.putParcelableArrayListExtra(InfoDetailActivity.BUNDLE_INFO,
                        (ArrayList<? extends Parcelable>) itemPhoneProduct.getListParameter());
                startActivity(intentInfo);
                break;
            case R.id.relative_comment:
                FragmentTransactionUtils.addFragment(
                        getSupportFragmentManager(),
                        CommentFragment.newInstance(itemPhoneProduct),
                        R.id.frame_full,
                        CommentFragment.TAG,
                        true,
                        -1, -1);
                break;
            case R.id.fab_cart:
                CartItem cartItem = new CartItem(
                        itemPhoneProduct.getTitle(),
                        itemPhoneProduct.getPrice(),
                        1,
                        itemPhoneProduct.getImage(),
                        itemPhoneProduct.getId()
                );
                RealmCart.addToCart(cartItem);
                onUpdateCart();

                AddCartBottomDialogFragment addCartFragment =
                        AddCartBottomDialogFragment.newInstance(itemPhoneProduct);
                addCartFragment.show(getSupportFragmentManager(),
                        "add_cart_fragment");
                break;
        }
    }

    @Override
    public void onClickSlider(List<String> sliders, int position) {
        FragmentTransactionUtils.addFragment(
                getSupportFragmentManager(),
                SliderImageFragment.newInstance(sliders, position),
                R.id.frame_full, SliderImageFragment.TAG,
                true, -1, -1
        );
    }

    @Override
    public void onHideButtonCart(int visibility) {
        //mImageShopping.setVisibility(View.GONE);
    }
}
