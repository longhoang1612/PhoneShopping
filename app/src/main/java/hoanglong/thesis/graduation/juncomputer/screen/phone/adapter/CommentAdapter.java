package hoanglong.thesis.graduation.juncomputer.screen.phone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hoanglong.thesis.graduation.juncomputer.R;
import hoanglong.thesis.graduation.juncomputer.data.model.comment.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Comment> mComments;

    public CommentAdapter(List<Comment> comments) {
        mComments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_comment, viewGroup, false);
        return new CommentViewHolder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        Comment comment = mComments.get(i);
        commentViewHolder.bindData(comment);
    }

    @Override
    public int getItemCount() {
        return mComments != null ? mComments.size() : 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_title_header)
        TextView mTextHeader;
        @BindView(R.id.rating_comment)
        RatingBar mRatingComment;
        @BindView(R.id.date_comment)
        TextView mTextDate;
        @BindView(R.id.text_user_comment)
        TextView mTextUserComment;
        @BindView(R.id.text_comment)
        TextView mTextComment;
        @BindView(R.id.image_comment)
        ImageView mImageComment;
        private Context mContext;

        CommentViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
        }

        public void bindData(Comment comment) {
            if (comment == null) {
                return;
            }
            mTextHeader.setText(comment.getTitleComment());
            mRatingComment.setRating(comment.getRating());
            mTextDate.setText(comment.getDate());
            mTextUserComment.setText(comment.getNameUser());
            mTextComment.setText(comment.getComment());
            if (comment.getImageComment().isEmpty()) {
                mImageComment.setVisibility(View.GONE);
            } else {
                mImageComment.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(comment.getImageComment()).into(mImageComment);
            }
        }
    }
}
