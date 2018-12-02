package hoanglong.thesis.graduation.juncomputer.screen.bottomsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.phone_product.ItemPhoneProduct;
import hoanglong.thesis.graduation.juncomputer.screen.cart.CartActivity;

public class AddCartBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    public static final String BUNDLE_ITEM_CART = "BUNDLE_ITEM_CART";
    private ItemPhoneProduct mItemPhoneProduct;
    @BindView(R.id.image_product)
    ImageView mImageProduct;
    @BindView(R.id.text_name_product)
    TextView mTextNameProduct;
    @BindView(R.id.text_price_product)
    TextView mTextPriceProduct;
    @BindView(R.id.relative_view_cart)
    RelativeLayout mRelativeViewCart;

    public static AddCartBottomDialogFragment newInstance(ItemPhoneProduct itemPhoneProduct) {

        Bundle bundle = new Bundle();
        AddCartBottomDialogFragment fragment = new AddCartBottomDialogFragment();
        bundle.putParcelable(BUNDLE_ITEM_CART,itemPhoneProduct);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mItemPhoneProduct = bundle.getParcelable(BUNDLE_ITEM_CART);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottomsheet_add_product, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        setUpView();
    }

    private void setUpView() {
        Glide.with(getContext()).load(mItemPhoneProduct.getImage()).into(mImageProduct);
        mTextNameProduct.setText(mItemPhoneProduct.getTitle());
        mTextPriceProduct.setText(mItemPhoneProduct.getPrice());
    }

    private void initListener() {
        mRelativeViewCart.setOnClickListener(this);
    }

    private void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relative_view_cart:
                Intent intent = new Intent(getActivity(),CartActivity.class);
                startActivity(intent);
                break;
        }
    }
}
