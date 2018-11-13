package hoanglong.thesis.graduation.juncomputer.data.source.local;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;
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
                "https://cdn.tgdd.vn/Products/Images/42/190321/iphone-xs-max-gray-600x600.jpg",
                Constant.Category.phone,
                Constant.Category.phone_type);
        Category category2 = new Category(
                "https://cdn.tgdd.vn/Products/Images/522/163791/ipad-wifi-128-gb-2018-thumb-400x400.jpg",
                Constant.Category.table,
                Constant.Category.tablet_type);
        Category category3 = new Category(
                "https://cdn.tgdd.vn/Products/Images/44/177638/hp-pavilion-14-ce0021tu-i-4mf00pa-33397-ava1-400x400.jpg",
                Constant.Category.laptop,
                Constant.Category.laptop_type);
        Category category4 = new Category(
                "https://cdn.tgdd.vn/Products/Images/58/88354/cap-lightning-1m-evalu-ltl-01-avatar-1-600x600.jpg",
                Constant.Category.accessories,
                Constant.Category.accessories_type);
        Category category5 = new Category(
                "https://cdn.tgdd.vn/Products/Images/42/172404/iphone-x-256gb-silver-400x400.jpg",
                Constant.Category.old_phone,
                Constant.Category.old_phone_type);
        Category category6 = new Category("https://cdn.tgdd.vn/Products/Images/7077/74701/apple-watch-s1-42mm-cthumb-400x400.jpg",
                Constant.Category.clock,Constant.Category.clock_type);

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
