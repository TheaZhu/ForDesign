package com.thea.fordesign.like.user;


import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUserLike;
import com.thea.fordesign.databinding.LaconicShotItemBinding;
import com.thea.fordesign.databinding.ShotsFragBinding;
import com.thea.fordesign.shot.detail.ShotDetailActivity;
import com.thea.fordesign.util.Preconditions;
import com.thea.fordesign.widget.FooterSpanSizeLookup;
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
public class UserLikesFragment extends BaseDataBindingFragment<ShotsFragBinding> implements
        UserLikesContract.View {
    public static final String TAG = UserLikesFragment.class.getSimpleName();
    public static final String ARG_LIKE_URL = "like_url";

    private String mLikesUrl;

    private UserLikesContract.Presenter mPresenter;

    private ShotAdapter mAdapter;
    private LoadMoreListener mLoadMoreListener;
    private MyLoadingView mLoadingView;

    public UserLikesFragment() {
    }

    public static UserLikesFragment newInstance(String likesUrl) {
        UserLikesFragment fragment = new UserLikesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_LIKE_URL, likesUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLikesUrl = getArguments().getString(ARG_LIKE_URL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shots;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBinding.srlShots.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);
        mViewDataBinding.srlShots.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadLikes(mLikesUrl);
            }
        });

        final RecyclerView recyclerView = mViewDataBinding.rvShots;
        mAdapter = new ShotAdapter(new ArrayList<DribbbleUserLike>(), mPresenter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new FooterSpanSizeLookup(layoutManager));
        recyclerView.setLayoutManager(layoutManager);
        mLoadingView = new MyLoadingView(getContext(), recyclerView);
        recyclerView.setAdapter(new FooterWrapAdapter(mAdapter, mLoadingView.getView()));
        mLoadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.loadMore(mLikesUrl, currentPage);
            }
        };
        mLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoadMoreListener.onScrolled(recyclerView, 0, 2);
            }
        }, false);
        recyclerView.addOnScrollListener(mLoadMoreListener);

        mPresenter.loadLikes(mLikesUrl);
    }

    @Override
    public void setRefreshingIndicator(final boolean active) {
        SwipeRefreshLayout srl = mViewDataBinding.srlShots;
        if (srl == null || srl.isRefreshing() == active) {
            return;
        }
        Observable.just(active).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        mViewDataBinding.srlShots.setRefreshing(active);
                    }
                });
    }

    @Override
    public void setLoadingIndicator(boolean visible, boolean active, @StringRes int resId, boolean enableClick) {
        RecyclerView.Adapter adapter = mViewDataBinding.rvShots.getAdapter();
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
    public void showLikes(List<DribbbleUserLike> likes) {
        mAdapter.replaceData(likes);
    }

    @Override
    public void insertLikes(List<DribbbleUserLike> likes) {
        mAdapter.insertData(likes);
    }

    @Override
    public void showShotDetailsUi(int shotId, String imageUrl, View v) {
        Intent intent = new Intent(getContext(), ShotDetailActivity.class);
        intent.putExtra(ShotDetailActivity.EXTRA_SHOT_ID, shotId);
        intent.putExtra(ShotDetailActivity.EXTRA_SHOT_IMAGE_URL, imageUrl);
        if (Build.VERSION.SDK_INT >= 21) {
            View sharedView = v.findViewById(R.id.iv_shot);
            String transitionName = getString(R.string.image_shot);

            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);

            startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void setPresenter(@NonNull UserLikesContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter, "presenter cannot be null");
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

    public class ShotAdapter extends RecyclerView.Adapter<ShotViewHolder> {
        private List<DribbbleUserLike> mItems;

        private UserLikesContract.Presenter mUserActionsListener;

        public ShotAdapter(List<DribbbleUserLike> items, UserLikesContract.Presenter itemListener) {
            mItems = items;
            mUserActionsListener = itemListener;
        }

        public void replaceData(List<DribbbleUserLike> items) {
            mItems = items;
            notifyDataSetChanged();
        }

        public void insertData(List<DribbbleUserLike> items) {
            int start = mItems.size();
            mItems.addAll(items);
            notifyItemRangeInserted(start, items.size());
        }

        @Override
        public ShotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LaconicShotItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from
                    (parent.getContext()), R.layout.card_shot_without_infos, parent, false);
            return new ShotViewHolder(viewDataBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(ShotViewHolder holder, int position) {
            LaconicShotItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
            DribbbleShot shot = mItems.get(position).getShot();
            viewDataBinding.setShot(shot);
            viewDataBinding.setActionHandler(mUserActionsListener);

            Glide.with(UserLikesFragment.this)
                    .load(shot.getImage())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .placeholder(R.mipmap.default_shot)
                    .crossFade()
                    .into(viewDataBinding.ivShot);

            viewDataBinding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public static class ShotViewHolder extends RecyclerView.ViewHolder {

        public ShotViewHolder(View itemView) {
            super(itemView);
        }
    }
}
