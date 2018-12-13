package hoanglong.thesis.graduation.juncomputer.screen.manageOrder;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.DetailOrderFragment;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.order.Order;
import hoanglong.thesis.graduation.juncomputer.data.model.order.OrderUpload;
import hoanglong.thesis.graduation.juncomputer.data.model.user.User;
import hoanglong.thesis.graduation.juncomputer.screen.base.BaseFragment;
import hoanglong.thesis.graduation.juncomputer.screen.home.HomeActivity;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.UserInfoActivity;
import hoanglong.thesis.graduation.juncomputer.screen.userinfo.adapter.OrderManagerAdapter;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import hoanglong.thesis.graduation.juncomputer.utils.Constant;
import hoanglong.thesis.graduation.juncomputer.utils.FragmentTransactionUtils;
import hoanglong.thesis.graduation.juncomputer.utils.SharedPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerOrderFragment extends BaseFragment implements OrderManagerAdapter.OnClickOrderListener {

    public static final String TAG = ManagerOrderFragment.class.getName();
    @BindView(R.id.recycler_manager_cart)
    RecyclerView mRecyclerOrder;
    @BindView(R.id.progress_order)
    ProgressBar mProgressOrder;
    OrderManagerAdapter mOrderManagerAdapter;
    @BindView(R.id.relative_empty)
    RelativeLayout mRelativeEmpty;
    @BindView(R.id.ic_back)
    ImageView mImageBack;
    private List<Order> mOrders;
    private Context mContext;

    @Override
    protected int getLayoutResources() {
        return R.layout.fragment_manager_order;
    }

    @Override
    protected void initComponent(View view) {
        ButterKnife.bind(this, view);
        mProgressOrder.setVisibility(View.VISIBLE);
        mRecyclerOrder.setVisibility(View.GONE);
        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mOrders = new ArrayList<>();
        Gson gson = new Gson();
        String json = SharedPrefs.getInstance().get(Constant.Login.OBJECT_USER_LOGIN, String.class);
        User user = gson.fromJson(json, User.class);
        if (user == null) {
            return;
        }
        Call<OrderUpload> call = BaseService.getService().getOrderByUser(user.getId());
        call.enqueue(new Callback<OrderUpload>() {
            @Override
            public void onResponse(@NonNull Call<OrderUpload> call, @NonNull Response<OrderUpload> response) {
                if (response.body() != null) {
                    mOrders = response.body().getOrders();
                    if (mOrders.size() == 0) {
                        mProgressOrder.setVisibility(View.GONE);
                        mRecyclerOrder.setVisibility(View.GONE);
                        mRelativeEmpty.setVisibility(View.VISIBLE);
                    } else {
                        mProgressOrder.setVisibility(View.GONE);
                        mRecyclerOrder.setVisibility(View.VISIBLE);
                        mRelativeEmpty.setVisibility(View.GONE);
                    }
                    mOrderManagerAdapter =
                            new OrderManagerAdapter(mOrders, ManagerOrderFragment.this);
                    mRecyclerOrder.setAdapter(mOrderManagerAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderUpload> call, @NonNull Throwable t) {
                mProgressOrder.setVisibility(View.GONE);
                mRecyclerOrder.setVisibility(View.GONE);
                mRelativeEmpty.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClickOrder(Order order) {
        if (order == null) {
            return;
        }
        if (getFragmentManager() != null) {
            if (mContext instanceof UserInfoActivity) {
                FragmentTransactionUtils.addFragment(
                        getFragmentManager(),
                        DetailOrderFragment.newInstance(order), R.id.frame_user_info, DetailOrderFragment.TAG,
                        true, -1, -1);
            } else if (mContext instanceof HomeActivity) {
                FragmentTransactionUtils.addFragment(
                        getFragmentManager(),
                        DetailOrderFragment.newInstance(order), R.id.frame_full_home, DetailOrderFragment.TAG,
                        true, -1, -1);
            }
        }
    }
}
