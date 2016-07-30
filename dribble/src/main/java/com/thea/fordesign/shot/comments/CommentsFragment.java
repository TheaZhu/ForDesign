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
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.CommentItemBinding;
import com.thea.fordesign.databinding.CommentsFragBinding;
import com.thea.fordesign.user.detail.UserDetailActivity;
import com.thea.fordesign.util.Preconditions;
import com.thea.fordesign.widget.FooterWrapAdapter;
import com.thea.fordesign.widget.LoadMoreListener;
import com.thea.fordesign.widget.MyLoadingView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends BaseDataBindingFragment<CommentsFragBinding> implements
        CommentContract.View {
    public static final String TAG = CommentsFragment.class.getSimpleName();

    private CommentContract.Presenter mPresenter;

    private CommentAdapter mAdapter;
    private LoadMoreListener mLoadMoreListener;
    private MyLoadingView mLoadingView;

    public CommentsFragment() {
    }

    public static CommentsFragment newInstance() {
        return new CommentsFragment();
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

        mViewDataBinding.srlComments.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);
        mViewDataBinding.srlComments.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadComments();
            }
        });

        final RecyclerView recyclerView = mViewDataBinding.rvShotComments;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CommentAdapter(new ArrayList<DribbbleComment>(), mPresenter);
        mLoadingView = new MyLoadingView(getContext(), recyclerView);
        recyclerView.setAdapter(new FooterWrapAdapter(mAdapter, mLoadingView.getView()));
        mLoadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.loadMore(currentPage);
            }
        };
        mLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoadMoreListener.onScrolled(recyclerView, 0, 2);
            }
        }, false);
        recyclerView.addOnScrollListener(mLoadMoreListener);

        mPresenter.loadComments();
    }

    @Override
    public void setRefreshingIndicator(final boolean active) {
        SwipeRefreshLayout srl = mViewDataBinding.srlComments;
        if (srl == null || srl.isRefreshing() == active) {
            return;
        }
        Observable.just(active).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        mViewDataBinding.srlComments.setRefreshing(active);
                    }
                });
    }

    @Override
    public void setLoadingIndicator(boolean visible, boolean active, @StringRes int resId, boolean enableClick) {
        RecyclerView.Adapter adapter = mViewDataBinding.rvShotComments.getAdapter();
        if (adapter instanceof FooterWrapAdapter) {
            ((FooterWrapAdapter) adapter).setLoading(visible);
        }
        mLoadingView.setLoadingIndicator(active, getString(resId));
        mLoadingView.enableClick(enableClick);
    }

    @Override
    public void setLoadingError() {
        mLoadMoreListener.setLoadingError();
    }

    @Override
    public void showComments(List<DribbbleComment> comments) {
        mAdapter.replaceData(comments);
    }

    @Override
    public void insertComments(List<DribbbleComment> comments) {
        mAdapter.insertData(comments);
    }

    @Override
    public void showCommentLiked() {
    }

    @Override
    public void showCommentDisliked() {
    }

    @Override
    public void showUserDetailsUi(@NonNull DribbbleUser requestedUser, View v) {
        Intent intent = new Intent(getActivity(), UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.EXTRA_USER, requestedUser);
        if (Build.VERSION.SDK_INT >= 21) {
            String transitionName = getString(R.string.image_user_avatar);

            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), v, transitionName);

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
    public void setCanAddComment(boolean canAddComment) {
        mViewDataBinding.rlAddComment.setVisibility(canAddComment ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setPresenter(@NonNull CommentContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter, "presenter cannot be null");
        ;
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
            CommentItemActionHandler actionHandler = new CommentItemActionHandler
                    (mUserActionsListener);
            DribbbleComment item = mItems.get(position);
            viewDataBinding.setComment(item);
            viewDataBinding.setActionHandler(actionHandler);

            Glide.with(CommentsFragment.this)
                    .load(item.getUser().getAvatarUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .placeholder(R.mipmap.default_user_avatar)
                    .into(viewDataBinding.ivUserAvatar);
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
