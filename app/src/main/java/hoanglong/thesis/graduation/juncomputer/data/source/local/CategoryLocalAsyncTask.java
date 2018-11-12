package hoanglong.thesis.graduation.juncomputer.data.source.local;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.Category;
import hoanglong.thesis.graduation.juncomputer.data.source.CallBack;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;

public class CategoryLocalAsyncTask extends AsyncTask<String, String, List<Category>> {

    private CallBack<List<Category>> mCallBack;

    CategoryLocalAsyncTask(CallBack<List<Category>> mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    protected List<Category> doInBackground(String... strings) {
        List<Category> mCategories = new ArrayList<>();
        Category category1 = new Category(
                "",
                Constant.Category.phone,
                Constant.Category.phone_type);
        Category category2 = new Category(
                "",
                Constant.Category.table,
                Constant.Category.tablet_type);
        Category category3 = new Category(
                "",
                Constant.Category.laptop,
                Constant.Category.laptop_type);
        Category category4 = new Category(
                "",
                Constant.Category.accessories,
                Constant.Category.accessories_type);
        Category category5 = new Category(
                "",
                Constant.Category.old_phone,
                Constant.Category.old_phone_type);
        Category category6 = new Category(
                "",
                Constant.Category.sale,
                Constant.Category.sale_type);

        mCategories.add(category1);
        mCategories.add(category2);
        mCategories.add(category3);
        mCategories.add(category4);
        mCategories.add(category5);
        mCategories.add(category6);
        return mCategories;
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        super.onPostExecute(categories);
        if (mCallBack == null) {
            return;
        }
        mCallBack.getDataSuccess(categories);
    }
}
