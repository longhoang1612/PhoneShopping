package hoanglong.thesis.graduation.juncomputer.screen.userinfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    @BindView(R.id.swipe_seen)
    SwipeRefreshLayout mSwipeRefreshSeen;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_seen;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        mSwipeRefreshSeen.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshSeen.setRefreshing(false);
                        setDataSeen();
                    }
                },500);
            }
        });
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        setDataSeen();
    }

    private void setDataSeen() {
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
