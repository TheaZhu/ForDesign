package com.thea.fordesign.like.shot;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleShotLike;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.ShotsFragBinding;
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
public class ShotLikesFragment extends BaseDataBindingFragment<ShotsFragBinding> implements
        ShotLikesContract.View {
    public static final String TAG = ShotLikesFragment.class.getSimpleName();

    private ShotLikesContract.Presenter mPresenter;

    private ShotLikeAdapter mAdapter;
    private LoadMoreListener mLoadMoreListener;
    private MyLoadingView mLoadingView;

    public ShotLikesFragment() {
    }

    public static ShotLikesFragment newInstance() {
        return new ShotLikesFragment();
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
        return R.layout.fragment_users;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBinding.srlShots.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);
        mViewDataBinding.srlShots.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadLikes();
            }
        });

        final RecyclerView recyclerView = mViewDataBinding.rvShots;
        mAdapter = new ShotLikeAdapter(this, new ArrayList<DribbbleShotLike>(), mPresenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
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

        mPresenter.loadLikes();
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
    public void setLoadingIndicator(boolean active, @StringRes int resId, boolean enableClick) {
        mLoadingView.setLoadingIndicator(active, getString(resId));
        mLoadingView.enableClick(enableClick);
    }

    @Override
    public void setLoadingError() {
        mLoadMoreListener.setLoadingError();
    }

    @Override
    public void showLikes(List<DribbbleShotLike> likes) {
        mAdapter.replaceData(likes);
    }

    @Override
    public void insertLikes(List<DribbbleShotLike> likes) {
        mAdapter.insertData(likes);
    }

    @Override
    public void showUserDetailsUi(@NonNull DribbbleUser user, View v) {
        Intent intent = new Intent(getContext(), UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.EXTRA_USER, user);
        if (Build.VERSION.SDK_INT >= 21) {
            View sharedView = v.findViewById(R.id.iv_avatar);
            String transitionName = getString(R.string.image_user_avatar);

            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);

            startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void setPresenter(@NonNull ShotLikesContract.Presenter presenter) {
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
}
