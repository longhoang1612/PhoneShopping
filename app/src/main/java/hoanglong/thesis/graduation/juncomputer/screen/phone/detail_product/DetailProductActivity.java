package hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.BuildConfig;
import hoanglong.thesis.graduation.juncomputer.DetailCommentFragment;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.cart.CartItem;
import hoanglong.thesis.graduation.juncomputer.data.model.comment.Comment;
import hoanglong.thesis.graduation.juncomputer.data.model.comment.CommentUpload;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.DetailContent;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ListParameter;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.PhoneProduct;
import hoanglong.thesis.graduation.juncomputer.data.model.user.Favorites;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmCart;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmFavorites;
import hoanglong.thesis.graduation.juncomputer.screen.bottomsheet.AddCartBottomDialogFragment;
import hoanglong.thesis.graduation.juncomputer.screen.cart.CartActivity;
import hoanglong.thesis.graduation.juncomputer.screen.home.adapter.SamplePagerAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.CommentAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.ContentAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.ExtraProductAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.InfoAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.SaleAdapter;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import hoanglong.thesis.graduation.juncomputer.utils.UploadCart;
import hoanglong.thesis.graduation.juncomputer.utils.customView.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity
        implements View.OnClickListener, SamplePagerAdapter.ClickSliderListener, Listener
        , CommentAdapter.OnClickCommentListener {

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
    @BindView(R.id.ic_shopping_cart)
    RelativeLayout mImageShopping;
    @BindView(R.id.progress_detail)
    ProgressBar mProgressDetail;
    @BindView(R.id.nest_scroll_detail)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.relative_sale)
    RelativeLayout mRelativeSale;
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    @BindView(R.id.ic_favorites)
    ImageView mImageFavorites;
    @BindView(R.id.recycler_comment)
    RecyclerView mRecyclerComment;
    @BindView(R.id.text_rating_avg_comment)
    TextView mTextRatingAVG;
    @BindView(R.id.rating_avg_comment)
    RatingBar mRatingAVG;
    @BindView(R.id.text_total_comment)
    TextView mTextTotalComment;

    @BindView(R.id.progress_five)
    ProgressBar mProgressFive;
    @BindView(R.id.text_five)
    TextView mTextFive;

    @BindView(R.id.progress_one)
    ProgressBar mProgressOne;
    @BindView(R.id.text_one)
    TextView mTextOne;

    @BindView(R.id.progress_two)
    ProgressBar mProgressTwo;
    @BindView(R.id.text_two)
    TextView mTextTwo;

    @BindView(R.id.progress_four)
    ProgressBar mProgressFour;
    @BindView(R.id.text_four)
    TextView mTextFour;

    @BindView(R.id.progress_three)
    ProgressBar mProgressThree;
    @BindView(R.id.text_three)
    TextView mTextThree;
    @BindView(R.id.image_share)
    ImageView mImageShare;

    private ItemPhoneProduct itemPhoneProduct;
    private List<DetailContent> mContentListHide;
    private List<ListParameter> mInfoProducts;
    private String title;
    private boolean mIsFavorites;
    private boolean mIsLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);
        mIsLogin = SharedPrefs.getInstance().get(Constant.Login.LOGIN_STATUS, Boolean.class);
        mRLAllContent.setOnClickListener(this);
        textSeeDetail.setOnClickListener(this);
        mRelativeComment.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
        mFABCart.setOnClickListener(this);
        mImageFavorites.setOnClickListener(this);
        mImageShopping.setOnClickListener(this);
        mImageShare.setOnClickListener(this);
        mContentListHide = new ArrayList<>();
        mInfoProducts = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("BUNDLE_ITEM_PRODUCT");
        }
        mNestedScrollView.setVisibility(View.GONE);
        mProgressDetail.setVisibility(View.VISIBLE);
        setData();
    }

    private void setDataComment(String id) {
        Call<CommentUpload> call = BaseService.getService().getComment(id);
        call.enqueue(new Callback<CommentUpload>() {
            @Override
            public void onResponse(@NonNull Call<CommentUpload> call, @NonNull Response<CommentUpload> response) {
                if (response.body() != null) {
                    List<Comment> comments = response.body().getComment();
                    mRecyclerComment.setAdapter(new CommentAdapter(comments, DetailProductActivity.this));

                    updateViewComment(comments);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentUpload> call, @NonNull Throwable t) {

            }
        });
    }

    private void updateViewComment(List<Comment> comments) {
        if (comments.size() == 0) return;
        float ratingAvg = 0;
        int rating1 = 0;
        int rating2 = 0;
        int rating3 = 0;
        int rating4 = 0;
        int rating5 = 0;

        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            ratingAvg += comment.getRating() / comments.size();

            if (comment.getRating() > 4) {
                rating5 += 1;
            } else if (comment.getRating() > 3 && comment.getRating() <= 4) {
                rating4 += 1;
            } else if (comment.getRating() > 2 && comment.getRating() <= 3) {
                rating3 += 1;
            } else if (comment.getRating() > 1 && comment.getRating() <= 2) {
                rating2 += 1;
            } else if (comment.getRating() == 1) {
                rating1 += 1;
            }
        }

        String progress5 = String.valueOf(rating5 / comments.size() * 100) + "%";
        String progress4 = String.valueOf(rating4 / comments.size() * 100) + "%";
        String progress3 = String.valueOf(rating3 / comments.size() * 100) + "%";
        String progress2 = String.valueOf(rating2 / comments.size() * 100) + "%";
        String progress1 = String.valueOf(rating1 / comments.size() * 100) + "%";

        mTextFive.setText(progress5);
        mTextFour.setText(progress4);
        mTextThree.setText(progress3);
        mTextTwo.setText(progress2);
        mTextOne.setText(progress1);

        mProgressFive.setProgress(rating5 / comments.size() * 100);
        mProgressFour.setProgress(rating4 / comments.size() * 100);
        mProgressThree.setProgress(rating3 / comments.size() * 100);
        mProgressTwo.setProgress(rating2 / comments.size() * 100);
        mProgressOne.setProgress(rating1 / comments.size() * 100);

        mTextRatingAVG.setText(String.valueOf(ratingAvg));
        mRatingAVG.setRating(ratingAvg);
        mTextTotalComment.setText(
                String.format("%s nhận xét",
                        String.valueOf(comments.size())
                ));
    }

    private void setData() {
        final Call<PhoneProduct> productCall = BaseService.getService().getPhoneWithTitle(title);
        productCall.enqueue(new Callback<PhoneProduct>() {
            @Override
            public void onResponse(@NonNull Call<PhoneProduct> call, @NonNull Response<PhoneProduct> response) {
                if (response.body() != null) {
                    mNestedScrollView.setVisibility(View.VISIBLE);
                    mProgressDetail.setVisibility(View.GONE);
                    itemPhoneProduct = response.body().getPhoneProduct().get(0);
                    setupView(itemPhoneProduct);
                    setSlide();
                    setDataComment(itemPhoneProduct.getId());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhoneProduct> call, @NonNull Throwable t) {
                mProgressDetail.setVisibility(View.VISIBLE);
                mNestedScrollView.setVisibility(View.GONE);
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
        onUpdateCart();
    }

    private void onUpdateFavorites(ItemPhoneProduct itemPhoneProduct) {
        if (RealmFavorites.getItemFavorites(itemPhoneProduct.getTitle()) != null) {
            mImageFavorites.setImageResource(R.drawable.ic_favorite_red);
            mIsFavorites = true;
        } else {
            mImageFavorites.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            mIsFavorites = false;
        }
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
        onUpdateFavorites(itemPhoneProduct);
        if (itemPhoneProduct.getDeal() == null || itemPhoneProduct.getDeal().equals("")) {
            mRelativeSale.setVisibility(View.GONE);
        } else {
            mRelativeSale.setVisibility(View.VISIBLE);
            mTextSale.setText(itemPhoneProduct.getDeal());
        }
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
                if (mIsLogin) {
                    UploadCart.uploadCart();
                }
                onUpdateCart();

                AddCartBottomDialogFragment addCartFragment =
                        AddCartBottomDialogFragment.newInstance(itemPhoneProduct);
                addCartFragment.show(getSupportFragmentManager(),
                        "add_cart_fragment");
                break;
            case R.id.ic_back:
                onBackPressed();
                break;
            case R.id.ic_favorites:
                favoritesItem();
                break;
            case R.id.image_share:
                shareProduct();
                break;
            case R.id.ic_shopping_cart:
                Intent intent1 = new Intent(DetailProductActivity.this, CartActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void shareProduct() {
        Picasso.get().load(itemPhoneProduct.getImage()).into(target);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            shareImage(bitmap);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };


    private void shareImage(Bitmap bitmap) {
        // save bitmap to cache directory
        try {
            File cachePath = new File(this.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(this.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png/");
            shareIntent.setType("text/plain");
            String shareBody = "Mua ngay sản phẩm tại ứng dụng MayBE: https://play.google.com/store/apps/developer?id=May+BE&hl=vi";
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, itemPhoneProduct.getTitle());
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }

    @Override
    public void onDestroy() {
        Picasso.get().cancelRequest(target);
        super.onDestroy();
    }

    private void favoritesItem() {
        if (mIsFavorites) {
            mIsFavorites = false;
            Toast.makeText(this, "Xóa sản phẩm khỏi yêu thích", Toast.LENGTH_SHORT).show();
            RealmFavorites.unFavorites(itemPhoneProduct.getTitle());
            mImageFavorites.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            mIsFavorites = true;
            Toast.makeText(this, "Thêm sản phẩm vào yêu thích", Toast.LENGTH_SHORT).show();
            String title = itemPhoneProduct.getTitle();
            String image = itemPhoneProduct.getImage();
            String price = itemPhoneProduct.getPrice();
            int rating = itemPhoneProduct.getRating();
            String numberRating = itemPhoneProduct.getNumberRating();

            Favorites favorites = new Favorites(title, image, price, rating, numberRating);
            RealmFavorites.favorites(favorites);
            mImageFavorites.setImageResource(R.drawable.ic_favorite_red);
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
    public void onUpdateComment() {
        setData();
    }

    @Override
    public void onClickComment(Comment comment) {
        FragmentTransactionUtils.addFragment(
                getSupportFragmentManager(),
                DetailCommentFragment.newInstance(comment),
                R.id.frame_full, SliderImageFragment.TAG,
                true, -1, -1
        );
    }
}
