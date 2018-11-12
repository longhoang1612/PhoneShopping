package hoanglong.thesis.graduation.juncomputer.screen.category.sub_category.phone.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.detailPhone.ListParameter;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder> {

    private List<ListParameter> mParameterList;
    private LayoutInflater mInflater;

    public InfoAdapter(List<ListParameter> parameterList) {
        mParameterList = parameterList;
    }

    @NonNull
    @Override
    public InfoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mInflater==null){
            mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mInflater.inflate(R.layout.item_info_product,viewGroup,false);
        return new InfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoHolder infoHolder, int i) {
        ListParameter listParameter = mParameterList.get(i);
        infoHolder.bindData(listParameter);
        if(i%2==1){
            infoHolder.mLinearInfo.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return mParameterList!=null?mParameterList.size():0;
    }

    class InfoHolder extends RecyclerView.ViewHolder {

        private TextView mTextInfo;
        private TextView mTextDetail;
        private LinearLayout mLinearInfo;

        InfoHolder(@NonNull View itemView) {
            super(itemView);
            mTextInfo = itemView.findViewById(R.id.text_title_info);
            mTextDetail = itemView.findViewById(R.id.text_detail_info);
            mLinearInfo = itemView.findViewById(R.id.linear_info);
        }

        void bindData(ListParameter listParameter) {
            if(listParameter==null){
                return;
            }
            mTextDetail.setText(listParameter.getContentPara());
            mTextInfo.setText(listParameter.getTitlePara());
        }
    }
}
