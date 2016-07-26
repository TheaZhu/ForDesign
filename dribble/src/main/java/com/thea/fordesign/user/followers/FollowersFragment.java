package com.thea.fordesign.user.followers;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleFollower;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.UsersFragBinding;
import com.thea.fordesign.user.detail.UserDetailActivity;
import com.thea.fordesign.util.Preconditions;
import com.thea.fordesign.widget.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * A simple {@link BaseDataBindingFragment} subclass.
 */
public class FollowersFragment extends BaseDataBindingFragment<UsersFragBinding> implements
        FollowersContract.View {
    public static final String TAG = FollowersFragment.class.getSimpleName();
    public static final String ARG_FOLLOWER_URL = "follower_url";
    public static final String ARG_ITEM_TYPE = "item_type";
    public static final String ARG_HAS_MENU = "has_menu";

    public static final int TYPE_FOLLOWER = 0x01;
    public static final int TYPE_FOLLOWING = 0x02;

    private String mUrl;
    private int mItemType;
    private boolean mHasMenu = false;

    private FollowersContract.Presenter mPresenter;
    private FollowerAdapter mAdapter;

    public FollowersFragment() {
    }

    public static FollowersFragment newInstance(String url, int itemType, boolean hasMenu) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_FOLLOWER_URL, url);
        bundle.putInt(ARG_ITEM_TYPE, itemType);
        bundle.putBoolean(ARG_HAS_MENU, hasMenu);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_FOLLOWER_URL);
            mItemType = getArguments().getInt(ARG_ITEM_TYPE, TYPE_FOLLOWER);
            mHasMenu = getArguments().getBoolean(ARG_HAS_MENU, false);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mHasMenu) {
            inflater.inflate(R.menu.menu_shots_fragment, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_users;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(mHasMenu);
        mViewDataBinding.srlUsers.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);
        mViewDataBinding.srlUsers.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFollowers();
            }
        });

        RecyclerView recyclerView = mViewDataBinding.rvUsers;
        mAdapter = new FollowerAdapter(this, new ArrayList<DribbbleFollower>(), mPresenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        LoadMoreListener loadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMore(currentPage);
            }
        };
        recyclerView.addOnScrollListener(loadMoreListener);

        loadFollowers();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        final SwipeRefreshLayout srl = mViewDataBinding.srlUsers;
        if (srl == null || srl.isRefreshing() == active) {
            return;
        }

        Observable.just(active).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        mViewDataBinding.srlUsers.setRefreshing(active);
                    }
                });
    }

    @Override
    public void showFollowers(List<DribbbleFollower> followers) {
        mAdapter.replaceData(followers);
    }

    @Override
    public void insertFollowers(List<DribbbleFollower> followers) {
        mAdapter.insertData(followers);
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
    public void setPresenter(@NonNull FollowersContract.Presenter presenter) {
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

    private void loadFollowers() {
        mPresenter.loadFollowers(mUrl);
    }

    private void loadMore(int page) {
        mPresenter.loadMore(mUrl, page);
    }
}
