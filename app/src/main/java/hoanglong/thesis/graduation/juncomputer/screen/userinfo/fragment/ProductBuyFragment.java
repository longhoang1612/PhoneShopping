package hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment;

import android.os.Bundle;
import android.view.View;

import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;

public class ProductBuyFragment extends BaseFragment {

    public static final String TAG = ProductBuyFragment.class.getName();

    public static ProductBuyFragment newInstance() {

        Bundle args = new Bundle();

        ProductBuyFragment fragment = new ProductBuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_product_buy;
    }

    @Override
    protected void initComponent(View view) {

    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }
}
