package hoanglong.thesis.graduation.juncomputer.screen.home.homefragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.home.NewsFeed;
import hoanglong.thesis.graduation.juncomputer.data.repository.HomeRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.remote.HomeDataSource;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.home.adapter.SamplePagerAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter.AccessoriesAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter.LaptopHighLightAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter.PhoneHighLightAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.home.homefragment.adapter.PhoneHomeAdapter;
import hoanglong.thesis.graduation.juncomputer.utils.customView.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends BaseFragment implements HomeContract.View {

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
        mHomePresenter = new HomePresenter(homeRepository);
        mHomePresenter.setView(this);
        loadNewsFeed();
    }

    private void loadNewsFeed() {
        mHomePresenter.getHome();
    }

    @Override
    public void onGetNewsFeedSuccess(NewsFeed newsFeed) {

        viewpager.setAdapter(new SamplePagerAdapter(
                newsFeed.getSlideImage().size(),
                newsFeed.getSlideImage()));
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
        mRecyclerPhoneHighLight.setAdapter(new PhoneHighLightAdapter(newsFeed.getPhone()));

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

    }

    @Override
    public void onGetDataError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
