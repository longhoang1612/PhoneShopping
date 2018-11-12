package hoanglong.thesis.graduation.juncomputer.data.repository;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.Category;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.data.source.CategoryDataSource;
import hoanglong.thesis.graduation.juncomputer.data.source.local.CategoryLocalDataSource;

public class CategoryRepository implements CategoryDataSource.localDataSource, CategoryDataSource.remoteDataSource {
    private static CategoryRepository sInstance;
    private CategoryLocalDataSource mCategoryLocalDataSource;

    private CategoryRepository(CategoryLocalDataSource categoryLocalDataSource) {
        mCategoryLocalDataSource = categoryLocalDataSource;
    }

    public static CategoryRepository getInstance(CategoryLocalDataSource categoryLocalDataSource) {
        if (sInstance == null)
            sInstance = new CategoryRepository(categoryLocalDataSource);
        return sInstance;
    }

    @Override
    public void getCategory(CallBack<List<Category>> callBack) {
        mCategoryLocalDataSource.getCategory(callBack);
    }
}
