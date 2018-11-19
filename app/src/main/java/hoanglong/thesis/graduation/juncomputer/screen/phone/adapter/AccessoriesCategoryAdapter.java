package hoanglong.thesis.graduation.juncomputer.screen.phone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.category.ItemPhoneCategory;

public class AccessoriesCategoryAdapter extends RecyclerView.Adapter<AccessoriesCategoryAdapter.PhoneCategoryViewHolder> {

    private List<ItemPhoneCategory> mPhoneCategoryList;
    private OnClickPhoneCategoryListener mCategoryListener;
    private LayoutInflater mInflater;

    public AccessoriesCategoryAdapter(List<ItemPhoneCategory> phoneCategoryList, OnClickPhoneCategoryListener categoryListener) {
        mPhoneCategoryList = phoneCategoryList;
        mCategoryListener = categoryListener;
    }

    @NonNull
    @Override
    public PhoneCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mInflater.inflate(R.layout.item_accessories_category, viewGroup, false);
        return new PhoneCategoryViewHolder(view, viewGroup.getContext(), mCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneCategoryViewHolder phoneCategoryViewHolder, int i) {
        ItemPhoneCategory phoneCategory = mPhoneCategoryList.get(i);
        phoneCategoryViewHolder.bindData(phoneCategory);
    }

    @Override
    public int getItemCount() {
        return mPhoneCategoryList != null ? mPhoneCategoryList.size() : 0;
    }

    public interface OnClickPhoneCategoryListener {
        void onClickItem(ItemPhoneCategory phoneCategory);
    }

    public static class PhoneCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext;
        private OnClickPhoneCategoryListener mCategoryListener;
        private TextView mImagePhoneCategory;
        private ItemPhoneCategory mPhoneCategory;

        PhoneCategoryViewHolder(@NonNull View itemView, Context context,
                                OnClickPhoneCategoryListener onClickPhoneCategoryListener) {
            super(itemView);
            mContext = context;
            mCategoryListener = onClickPhoneCategoryListener;
            mImagePhoneCategory = itemView.findViewById(R.id.image_phone_category);
            itemView.setOnClickListener(this);
        }

        void bindData(ItemPhoneCategory phoneCategory) {
            if (phoneCategory == null) {
                return;
            }
            mPhoneCategory = phoneCategory;
            mImagePhoneCategory.setText(phoneCategory.getImageCategory());
        }

        @Override
        public void onClick(View v) {
            mCategoryListener.onClickItem(mPhoneCategory);
        }
    }
}
