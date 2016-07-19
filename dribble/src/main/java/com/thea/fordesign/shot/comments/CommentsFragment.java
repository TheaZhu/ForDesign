package com.thea.fordesign.shot.comments;


import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleComment;
import com.thea.fordesign.databinding.CommentItemBinding;
import com.thea.fordesign.databinding.CommentsFragBinding;
import com.thea.fordesign.user.detail.UserDetailActivity;
import com.thea.fordesign.util.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends BaseDataBindingFragment<CommentsFragBinding> implements
        CommentContract.View {
    public static final String TAG = CommentsFragment.class.getSimpleName();

    public static final String ARG_COMMENTS_URL = "comments_url";

    private CommentContract.Presenter mPresenter;

    private String mCommentsUrl;

    private CommentAdapter mAdapter;

    public CommentsFragment() {
    }

    public static CommentsFragment newInstance(String commentsUrl) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_COMMENTS_URL, commentsUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCommentsUrl = getArguments().getString(ARG_COMMENTS_URL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comments;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mViewDataBinding.setActionHandler(mPresenter);

        RecyclerView recyclerView = mViewDataBinding.rvShotComments;
        mAdapter = new CommentAdapter(new ArrayList<DribbbleComment>(), mPresenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mPresenter.getComments(mCommentsUrl);
    }

    @Override
    public void showComments(List<DribbbleComment> comments) {
        mAdapter.replaceData(comments);
    }

    @Override
    public void showCommentLiked() {

    }

    @Override
    public void showCommentDisliked() {

    }

    @Override
    public void showUserDetailsUi(int userId, View v) {
        Intent intent = new Intent(getActivity(), UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.EXTRA_USER_ID, userId);
//        intent.putExtra(UserDetailActivity.EXTRA_AVATAR_URL, (String) sharedView.getTag());
        if (Build.VERSION.SDK_INT >= 21) {
            View sharedView = v.findViewById(R.id.iv_comment_avatar);
            String transitionName = getString(R.string.image_user_avatar);

            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);

            startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public String getNewComment() {
        return mViewDataBinding.etComment.getText().toString();
    }

    @Override
    public void setPresenter(@NonNull CommentContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter, "presenter cannot be null");;
    }

    @Override
    public void showSnack(final String msg) {
        final View view = mViewDataBinding.getRoot();
        view.post(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void showSnack(@StringRes int resId) {
        showSnack(getString(resId));
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
        private List<DribbbleComment> mItems;

        private CommentContract.Presenter mUserActionsListener;

        public CommentAdapter(List<DribbbleComment> items, CommentContract.Presenter itemListener) {
            mItems = items;
            mUserActionsListener = itemListener;
        }

        public void replaceData(List<DribbbleComment> items) {
            mItems = items;
            notifyDataSetChanged();
        }

        public void insertData(List<DribbbleComment> items) {
            int start = mItems.size();
            mItems.addAll(items);
            notifyItemRangeInserted(start, items.size());
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CommentItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent
                    .getContext()), R.layout.item_comment, parent, false);
            return new CommentViewHolder(viewDataBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            CommentItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
            CommentItemActionHandler actionHandler = new CommentItemActionHandler(mUserActionsListener);
            DribbbleComment item = mItems.get(position);
            viewDataBinding.setComment(item);
            viewDataBinding.setActionHandler(actionHandler);

            Glide.with(CommentsFragment.this)
                    .load(item.getUser().getAvatarUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .into(viewDataBinding.ivCommentAvatar);
            viewDataBinding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        public CommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
