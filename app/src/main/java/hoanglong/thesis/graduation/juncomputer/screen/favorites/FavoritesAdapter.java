package hoanglong.thesis.graduation.juncomputer.screen.favorites;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.user.Favorites;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmFavorites;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Favorites> mFavoritesList;
    private OnClickFavoritesListener mFavoritesListener;


    FavoritesAdapter(List<Favorites> favoritesList, OnClickFavoritesListener favoritesListener) {
        mFavoritesList = favoritesList;
        mFavoritesListener = favoritesListener;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_product_favorites, viewGroup, false);
        return new FavoritesViewHolder(view, viewGroup.getContext(), mFavoritesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder favoritesViewHolder, int i) {
        Favorites favorites = mFavoritesList.get(i);
        favoritesViewHolder.bindData(favorites, i);
    }

    @Override
    public int getItemCount() {
        return mFavoritesList != null ? mFavoritesList.size() : 0;
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext;
        private int mPosition;
        private OnClickFavoritesListener mListener;
        private Favorites mFavorites;

        @BindView(R.id.constraint_favorites)
        ConstraintLayout mConstraintFavorites;
        @BindView(R.id.image_close_favorites)
        ImageView mImageUnFavorites;
        @BindView(R.id.image_fav_product)
        ImageView mImageFavProduct;
        @BindView(R.id.text_title_fav_product)
        TextView mTitleFav;
        @BindView(R.id.text_number_rating_fav)
        TextView mTextNumberRating;
        @BindView(R.id.rating_bar_fav_product)
        RatingBar mRatingFav;
        @BindView(R.id.price_fav_product)
        TextView mTextPriceFav;

        FavoritesViewHolder(@NonNull View itemView, Context context, OnClickFavoritesListener onClickFavoritesListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
            mListener = onClickFavoritesListener;
            mImageUnFavorites.setOnClickListener(this);
            mConstraintFavorites.setOnClickListener(this);
        }

        public void bindData(Favorites favorites, int position) {
            if (favorites == null) {
                return;
            }
            mFavorites = favorites;
            mPosition = position;
            Glide.with(mContext).load(favorites.getImageFav()).into(mImageFavProduct);
            mTitleFav.setText(favorites.getTitleFav());
            mTextNumberRating.setText(favorites.getCountRatingFav());
            mRatingFav.setRating(favorites.getRatingFav());
            mTextPriceFav.setText(favorites.getPriceFav());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_close_favorites:
                    RealmFavorites.unFavorites(mFavorites.getTitleFav());
                    mListener.updateFavorites();
                    break;
                case R.id.constraint_favorites:
                    mListener.onClickFavoritesItem(mFavorites);
                    break;
            }
        }
    }

    interface OnClickFavoritesListener {
        void onClickFavoritesItem(Favorites favorites);
        void updateFavorites();
    }
}
