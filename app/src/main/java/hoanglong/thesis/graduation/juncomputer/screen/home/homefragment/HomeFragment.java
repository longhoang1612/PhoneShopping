package hoanglong.thesis.graduation.juncomputer.screen.home.homefragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import hoanglong.thesis.graduation.juncomputer.data.model.home.Phone;
import hoanglong.thesis.graduation.juncomputer.data.repository.CategoryRepository;
import hoanglong.thesis.graduation.juncomputer.data.repository.HomeRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.local.CategoryLocalDataSource;
import hoanglong.thesis.graduation.juncomputer.data.source.remote.HomeDataSource;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.home.adapter.SamplePagerAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter.AccessoriesAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter.CategoryHomeAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter.LaptopHighLightAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter.PhoneHighLightAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter.PhoneHomeAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.main.MainActivity;
import hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product.DetailProductActivity;
import hoanglong.thesis.graduation.juncomputer.screen.phone.phone_category.PhoneCategoryFragment;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.customView.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends BaseFragment implements HomeContract.View
        , SamplePagerAdapter.ClickSliderListener, PhoneHighLightAdapter.ClickPhoneListener,
        CategoryHomeAdapter.OnClickCategoryItem {

    public static final String TAG = HomeFragment.class.getName();

    @BindView(R.id.viewpager)
    LoopViewPager viewpager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.recycler_phone_home)
    RecyclerView mRecyclerPhoneHome;
    @BindView(R.id.recycler_phone_highlight)
    RecyclerView mRecyclerPhoneHighLight;
    @BindView(R.id.recycler_laptop)
    RecyclerView mRecyclerLaptop;
    @BindView(R.id.recycler_accessories)
    RecyclerView mRecyclerAccessories;
    @BindView(R.id.recycler_category_home)
    RecyclerView mRecyclerCategoryHome;
    @BindView(R.id.progress_home)
    ProgressBar mProgressHome;
    @BindView(R.id.linear_home)
    LinearLayout mLinearHome;
    @BindView(R.id.swipe_home)
    SwipeRefreshLayout mSwipeRefreshHome;
    private HomePresenter mHomePresenter;

    public HomeFragment() {
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        HomeDataSource homeDataSource = HomeDataSource.getInstance();
        HomeRepository homeRepository = HomeRepository.getInstance(homeDataSource);

        CategoryLocalDataSource localDataSource = CategoryLocalDataSource.getInstance();
        CategoryRepository categoryRepository = CategoryRepository.getInstance(localDataSource);

        mHomePresenter = new HomePresenter(homeRepository, categoryRepository);
        mHomePresenter.setView(this);
        loadNewsFeed();
        mSwipeRefreshHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNewsFeed();
                mSwipeRefreshHome.setRefreshing(false);
            }
        },1000);
    }

    private void loadNewsFeed() {
        mHomePresenter.getCategoryHome();
        mHomePresenter.getHome();
    }

    @Override
    public void onGetNewsFeedSuccess(NewsFeed newsFeed) {

        viewpager.setAdapter(new SamplePagerAdapter(
                newsFeed.getSlideImage(), this));
        indicator.setViewPager(viewpager);

        mRecyclerPhoneHome.setAdapter(new PhoneHomeAdapter(newsFeed.getKm()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 4) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        mRecyclerPhoneHighLight.setLayoutManager(gridLayoutManager);
        mRecyclerPhoneHighLight.setAdapter(new PhoneHighLightAdapter(newsFeed.getPhone(), this));

        GridLayoutManager gridLaptop = new GridLayoutManager(getContext(), 3);
        gridLaptop.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        mRecyclerLaptop.setLayoutManager(gridLaptop);
        mRecyclerLaptop.setAdapter(new LaptopHighLightAdapter(newsFeed.getLaptop()));

        mRecyclerAccessories.setAdapter(new AccessoriesAdapter(newsFeed.getAccessories()));
    }

    @Override
    public void hideProgressBar() {
        mProgressHome.setVisibility(View.GONE);
        mLinearHome.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetDataError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCategoryHomeSuccess(List<Category> categories) {
        CategoryHomeAdapter categoryAdapter = new CategoryHomeAdapter(categories, this);
        mRecyclerCategoryHome.setAdapter(categoryAdapter);
    }

    @Override
    public void onClickSlider(List<String> sliders, int position) {

    }

    @Override
    public void onClickPhone(Phone phone) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra("BUNDLE_ITEM_PRODUCT", phone.getTitle());
        startActivity(intent);
    }

    @Override
    public void onClickItem(Category category) {
        if (getFragmentManager() != null) {
            FragmentTransactionUtils.addFragment(
                    getFragmentManager(),
                    PhoneCategoryFragment.newInstance(category),
                    R.id.frame_home,
                    PhoneCategoryFragment.TAG,
                    true, -1, -1);
        }
    }
}
