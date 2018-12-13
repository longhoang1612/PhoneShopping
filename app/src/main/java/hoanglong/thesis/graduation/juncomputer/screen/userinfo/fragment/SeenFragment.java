package hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemSeen;
import hoanglong.thesis.graduation.juncomputer.data.source.local.realm.RealmSeen;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.PhoneSeenAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product.DetailProductActivity;

public class SeenFragment extends BaseFragment implements PhoneSeenAdapter.OnClickProductListener {

    public static final String TAG = SeenFragment.class.getName();
    @BindView(R.id.recycler_seen)
    RecyclerView mRecyclerSeen;
    @BindView(R.id.card_no_item)
    CardView mCardView;

    public static SeenFragment newInstance() {

        Bundle args = new Bundle();

        SeenFragment fragment = new SeenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_seen;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        if (RealmSeen.getListScreen().size() == 0) {
            mCardView.setVisibility(View.VISIBLE);
            mRecyclerSeen.setVisibility(View.GONE);
        } else {
            mCardView.setVisibility(View.GONE);
            mRecyclerSeen.setVisibility(View.VISIBLE);
            PhoneSeenAdapter seenAdapter = new PhoneSeenAdapter(RealmSeen.getListScreen(), this);
            mRecyclerSeen.setAdapter(seenAdapter);
        }
    }

    @Override
    public void onClickItemSeenProduct(ItemSeen itemPhoneProduct) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra("BUNDLE_ITEM_PRODUCT", itemPhoneProduct.getTitle());
        startActivity(intent);
    }
}
