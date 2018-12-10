package hoanglong.thesis.graduation.juncomputer.screen.favorites;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.AddressUser;
import hoanglong.thesis.graduation.juncomputer.data.model.user.Favorites;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmFavorites;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmUser;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product.DetailProductActivity;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FavoritesFragment extends BaseFragment implements View.OnClickListener,
        FavoritesAdapter.OnClickFavoritesListener, SwipeRefreshLayout.OnRefreshListener {

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
    private ProgressDialog dialogProgress;

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
        List<Favorites> favorites = RealmFavorites.getFavorites();
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(favorites, this);
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
                Toast.makeText(getContext(), "ABCD", Toast.LENGTH_SHORT).show();
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
        }, 2000);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void upLoadFavoritesWithUser() {
        showProgress();
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get("MyObject", String.class);
        User user = gson.fromJson(json, User.class);

        if (user == null) {
            return;
        }

        List<Favorites> favorites = new ArrayList<>(RealmFavorites.getFavorites());
        User uploadUser = new User(favorites);

        Call<User> call = BaseService.getService().updateFavorites(user.getEmail(), uploadUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                hideProgress();
                Toast.makeText(getContext(), "Update Favorites Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                t.getMessage();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showProgress() {
        if (dialogProgress != null) {
            return;
        }
        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Upload Favorites...");
        dialogProgress.show();
    }

    public void hideProgress() {
        if (dialogProgress == null)
            return;
        dialogProgress.dismiss();
        dialogProgress = null;
    }
}
