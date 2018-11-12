package hoanglong.thesis.graduation.juncomputer.category;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.category.subCategory.phone.PhoneCategoryFragment;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.data.model.Category;

public class CategoryFragment extends Fragment implements CategoryAdapter.OnClickCategoryItem {

    @BindView(R.id.recycler_category)
    RecyclerView mRecyclerCategory;
    private List<Category> mCategories;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        setDataCategory();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(mCategories,this);
        mRecyclerCategory.setAdapter(categoryAdapter);
    }

    private void setDataCategory() {
        mCategories = new ArrayList<>();
        Category category1 = new Category("",getString(R.string.title_phone),getString(R.string.type_phone));
        Category category2 = new Category("",getString(R.string.title_tablet),getString(R.string.type_tablet));
        Category category3 = new Category("",getString(R.string.title_laptop),getString(R.string.type_laptop));
        Category category4 = new Category("",getString(R.string.title_accessories),getString(R.string.type_accessories));
        Category category5 = new Category("",getString(R.string.title_old_phone),getString(R.string.type_old_phone));
        Category category6 = new Category("",getString(R.string.title_sale),getString(R.string.type_sale));

        mCategories.add(category1);
        mCategories.add(category2);
        mCategories.add(category3);
        mCategories.add(category4);
        mCategories.add(category5);
        mCategories.add(category6);
    }

    @Override
    public void onClickItem(Category category) {
        switch (category.getType()){
            case "phone":
                // TODO: 30/10/2018
                PhoneCategoryFragment phoneCategoryFragment = new PhoneCategoryFragment();
                if (getFragmentManager() != null) {
                    FragmentTransactionUtils.addFragment(
                            getFragmentManager(),
                            phoneCategoryFragment,
                            R.id.frame_home,
                            PhoneCategoryFragment.TAG,
                            true,-1,-1);
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
}
