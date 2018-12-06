package hoanglong.thesis.graduation.juncomputer.screen.search;

import android.os.Bundle;

import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_search;
    }

    @Override
    protected void initComponent() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
