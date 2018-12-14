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
    private OnClickCommentListener mCommentListener;

    public CommentAdapter(List<Comment> comments, OnClickCommentListener commentListener) {
        mComments = comments;
        mCommentListener = commentListener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_comment, viewGroup, false);
        return new CommentViewHolder(view, viewGroup.getContext(),mCommentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        Comment comment = mComments.get(i);
        commentViewHolder.bindData(comment);
    }

    public interface OnClickCommentListener{
        void onClickComment(Comment comment);
    }

    @Override
    public int getItemCount() {
        return mComments != null ? mComments.size() : 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
        private OnClickCommentListener mOnClickCommentListener;
        private Comment mComment;

        CommentViewHolder(@NonNull View itemView, Context context, OnClickCommentListener commentListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
            mOnClickCommentListener = commentListener;
            itemView.setOnClickListener(this);
        }

        public void bindData(Comment comment) {
            if (comment == null) {
                return;
            }
            mComment = comment;
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

        @Override
        public void onClick(View v) {
            mOnClickCommentListener.onClickComment(mComment);
        }
    }
}
