package hoanglong.thesis.graduation.juncomputer.data.source;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.data.model.category.Category;

public interface CategoryDataSource {
    interface remoteDataSource {
    }

    interface localDataSource {
        void getCategory(CallBack<List<Category>> callBack);
    }
}
