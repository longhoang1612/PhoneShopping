package hoanglong.thesis.graduation.juncomputer.data.source.local;

import java.util.List;
import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.data.source.CategoryDataSource;

public class CategoryLocalDataSource implements CategoryDataSource.localDataSource {

    private static CategoryLocalDataSource mInstance;

    public static CategoryLocalDataSource getInstance() {
        if (mInstance == null)
            mInstance = new CategoryLocalDataSource();
        return mInstance;
    }

    private void getDataHomeFormApi(CallBack<List<Category>> mCallBack) {
        new CategoryLocalAsyncTask(mCallBack).execute();
    }

    @Override
    public void getCategory(CallBack<List<Category>> callBack) {
        getDataHomeFormApi(callBack);
    }
}
