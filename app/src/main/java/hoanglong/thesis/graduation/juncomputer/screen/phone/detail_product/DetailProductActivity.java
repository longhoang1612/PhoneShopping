package hoanglong.thesis.graduation.juncomputer.screen.phone.detail_product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.detailPhone.AllDetailPhone;
import hoanglong.thesis.graduation.juncomputer.data.model.detailPhone.DetailPhone;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.ContentAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.ExtraProductAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.InfoAdapter;
import hoanglong.thesis.graduation.juncomputer.screen.phone.adapter.SaleAdapter;
import hoanglong.thesis.graduation.juncomputer.service.BaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    @BindView(R.id.text_sale)
    TextView mTextSale;
    @BindView(R.id.text_title_product)
    TextView mTextNameProduct;
    @BindView(R.id.text_price_product)
    TextView mTextPrice;
    @BindView(R.id.rating_bar_product)
    RatingBar mRatingBar;
    @BindView(R.id.text_number_rating_product)
    TextView mTextNumRating;
    @BindView(R.id.recycler_sale)
    RecyclerView mRecyclerSale;
    @BindView(R.id.recycler_extra_product)
    RecyclerView mRecyclerExtraProduct;
    @BindView(R.id.recycler_info_product)
    RecyclerView mRecyclerInfoProduct;
    @BindView(R.id.text_title_content)
    TextView mTextTitleContent;
//    @BindView(R.id.text_top_content_p)
//    TextView mTextTopContent;
    @BindView(R.id.text_h2)
    TextView mTextH2;
    @BindView(R.id.recycler_des)
    RecyclerView mRecyclerDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);
        setData();
    }

    private void setData() {
        Call<AllDetailPhone> call = BaseService.getService().getAllDetailPhone();
        call.enqueue(new Callback<AllDetailPhone>() {
            @Override
            public void onResponse(@NonNull Call<AllDetailPhone> call, @NonNull Response<AllDetailPhone> response) {
                if (response.body() != null) {
                    List<DetailPhone> allDetailPhone = response.body().getDetailPhone();
                    DetailPhone detailPhone = allDetailPhone.get(0);
                    setupView(detailPhone);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllDetailPhone> call, @NonNull Throwable t) {

            }
        });
    }

    private void setupView(DetailPhone detailPhone) {
        if(detailPhone==null){
            return;
        }
        mTextSale.setText(detailPhone.getSale());
        mTextNameProduct.setText(detailPhone.getTitle());
        mTextPrice.setText(detailPhone.getPrice());
        mRecyclerSale.setAdapter(
                new SaleAdapter(detailPhone.getListSale()));
        mRecyclerExtraProduct.setAdapter(
                new ExtraProductAdapter(detailPhone.getListExtraProduct()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerInfoProduct.getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerInfoProduct.addItemDecoration(dividerItemDecoration);
        mRecyclerInfoProduct.setAdapter(
                new InfoAdapter(detailPhone.getListParameter()));

        mTextTitleContent.setText(detailPhone.getTitleContent());
        mTextH2.setText(detailPhone.getTitleH2());
        mRecyclerDes.setAdapter(
                new ContentAdapter(detailPhone.getDetailContent()));
    }
}
