package hoanglong.thesis.graduation.juncomputer.screen.favorites;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemSeen;
import hoanglong.thesis.graduation.juncomputer.data.model.user.Favorites;
import hoanglong.thesis.graduation.juncomputer.data.model.user.FavoritesUpload;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmFavorites;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product.DetailProductActivity;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends BaseFragment implements View.OnClickListener,
        FavoritesAdapter.OnClickFavoritesListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = FavoritesFragment.class.getName();
    @BindView(R.id.recycler_favorites)
    RecyclerView mRecyclerFavorites;
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    @BindView(R.id.relative_no_favorites)
    RelativeLayout mRelativeNoFav;
    @BindView(R.id.swipe_favorites)
    SwipeRefreshLayout mSwipeRefreshFavorites;
    @BindView(R.id.up_load)
    ImageView mImageUpload;
    private List<Favorites> mFavorites;

    public static FavoritesFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_favorites;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mImageBack.setOnClickListener(this);
        mImageUpload.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        updateFavorites();
        upLoadFavoritesWithUser();
        mSwipeRefreshFavorites.setOnRefreshListener(this);
    }


    @Override
    public void updateFavorites() {
        if (RealmFavorites.getFavorites() == null || RealmFavorites.getFavorites().size() == 0) {
            mRecyclerFavorites.setVisibility(View.GONE);
            mRelativeNoFav.setVisibility(View.VISIBLE);
            return;
        }
        mRecyclerFavorites.setVisibility(View.VISIBLE);
        mRelativeNoFav.setVisibility(View.GONE);
        mFavorites = RealmFavorites.getFavorites();
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(mFavorites, this);
        mRecyclerFavorites.setAdapter(favoritesAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.up_load:
                upLoadFavoritesWithUser();
                break;
        }
    }

    @Override
    public void onClickFavoritesItem(Favorites favorites) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra("BUNDLE_ITEM_PRODUCT", favorites.getTitleFav());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshFavorites.setRefreshing(false);
                updateFavorites();
            }
        }, 1000);
    }

    private void upLoadFavoritesWithUser() {
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get(Constant.Login.OBJECT_USER_LOGIN, String.class);
        User user = gson.fromJson(json, User.class);

        if (user == null || mFavorites==null) {
            return;
        }

        ArrayList<Favorites> favorites = new ArrayList<>();
        for (int i = 0; i <= mFavorites.size() - 1; i++) {
            Favorites favRealm = mFavorites.get(i);
            Favorites favUpload = new Favorites(
                    favRealm.getImageFav(),
                    favRealm.getTitleFav(),
                    favRealm.getPriceFav(),
                    favRealm.getRatingFav(),
                    favRealm.getCountRatingFav()
            );
            favorites.add(favUpload);
        }

        FavoritesUpload favoritesUpload = new FavoritesUpload(favorites);

        Call<User> call = BaseService.getService().updateFavorites(user.getEmail(), favoritesUpload);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                t.getMessage();
            }
        });
    }
}
