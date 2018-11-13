package hoanglong.thesis.graduation.juncomputer.screen.category;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.data.repository.CategoryRepository;
import hoanglong.thesis.graduation.juncomputer.data.source.local.CategoryLocalDataSource;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.phone.phone_category.PhoneCategoryFragment;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;

public class CategoryFragment extends BaseFragment implements CategoryContract.View,
        CategoryAdapter.OnClickCategoryItem {

    @BindView(R.id.recycler_category)
    RecyclerView mRecyclerCategory;
    private CategoryPresenter mCategoryPresenter;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        CategoryLocalDataSource localDataSource = CategoryLocalDataSource.getInstance();
        CategoryRepository categoryRepository = CategoryRepository.getInstance(localDataSource);
        mCategoryPresenter = new CategoryPresenter(categoryRepository);
        mCategoryPresenter.setView(this);
        loadData();
    }

    private void loadData() {
        mCategoryPresenter.getCategories();
    }

    private void setUpRecyclerView(List<Category> categories) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
        mRecyclerCategory.setAdapter(categoryAdapter);
    }

    @Override
    public void onClickItem(Category category) {
        switch (category.getType()) {
            case "phone":
                PhoneCategoryFragment phoneCategoryFragment = new PhoneCategoryFragment();
                if (getFragmentManager() != null) {
                    FragmentTransactionUtils.addFragment(
                            getFragmentManager(),
                            phoneCategoryFragment,
                            R.id.frame_home,
                            PhoneCategoryFragment.TAG,
                            true, -1, -1);
                }
                break;
            case "tablet":
                // TODO: 30/10/2018
                break;
            case "laptop":
                // TODO: 30/10/2018
                break;
        }
    }

    @Override
    public void onGetDataSuccess(List<Category> categories) {
        setUpRecyclerView(categories);
    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void onGetDataError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
