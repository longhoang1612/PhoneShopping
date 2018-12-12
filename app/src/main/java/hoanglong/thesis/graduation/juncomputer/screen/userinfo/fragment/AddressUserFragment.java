package hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.payment.NewAddressFragment;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.adapter.AddressAdapter;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import retrofit2.http.Body;

public class AddressUserFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = AddressUserFragment.class.getName();
    @BindView(R.id.recycler_list_address)
    RecyclerView mRecyclerListAddress;
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    @BindView(R.id.relative_add_address)
    RelativeLayout mRelativeAddress;
    AddressAdapter mAddressAdapter;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_address_user;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mImageBack.setOnClickListener(this);
        mRelativeAddress.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back:
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.relative_add_address:
                if (getFragmentManager() != null) {
                    FragmentTransactionUtils.addFragment(getFragmentManager(),
                            new NewAddressFragment(),
                            R.id.frame_user_info,
                            NewAddressFragment.TAG, true, -1, -1);
                }
                break;
        }
    }
}
