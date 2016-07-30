package com.thea.fordesign.team.teams;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleTeam;
import com.thea.fordesign.databinding.TeamsFragBinding;
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
 * A simple {@link BaseDataBindingFragment} subclass.
 */
public class TeamsFragment extends BaseDataBindingFragment<TeamsFragBinding> implements
        TeamsContract.View {
    public static final String TAG = TeamsFragment.class.getSimpleName();

    private String mUrl;

    private TeamsContract.Presenter mPresenter;
    private TeamAdapter mAdapter;
    private MyLoadingView mLoadingView;
    private LoadMoreListener mLoadMoreListener;

    public TeamsFragment() {
    }

    public static TeamsFragment newInstance() {
        TeamsFragment fragment = new TeamsFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_teams;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBinding.srlTeams.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);
        mViewDataBinding.srlTeams.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTeams();
            }
        });

        final RecyclerView recyclerView = mViewDataBinding.rvTeams;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new TeamAdapter(this, new ArrayList<DribbbleTeam>(), mPresenter);
        mLoadingView = new MyLoadingView(getContext(), recyclerView);
        recyclerView.setAdapter(new FooterWrapAdapter(mAdapter, mLoadingView.getView()));
        mLoadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMore(currentPage);
            }
        };
        mLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoadMoreListener.onScrolled(recyclerView, 0, 2);
            }
        }, false);
        recyclerView.addOnScrollListener(mLoadMoreListener);

        loadTeams();
    }

    @Override
    public void setRefreshingIndicator(final boolean active) {
        final SwipeRefreshLayout srl = mViewDataBinding.srlTeams;
        if (srl == null || srl.isRefreshing() == active) {
            return;
        }
        Observable.just(active).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        mViewDataBinding.srlTeams.setRefreshing(active);
                    }
                });
    }

    @Override
    public void setLoadingIndicator(boolean visible, boolean active, @StringRes int resId, boolean
            enableClick) {
        RecyclerView.Adapter adapter = mViewDataBinding.rvTeams.getAdapter();
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
    public void showTeams(List<DribbbleTeam> teams) {
        mAdapter.replaceData(teams);
    }

    @Override
    public void insertTeams(List<DribbbleTeam> teams) {
        mAdapter.insertData(teams);
    }

    @Override
    public void showTeamDetailsUi(@NonNull DribbbleTeam team, View v) {
        /*Intent intent = new Intent(getContext(), TeamDetailActivity.class);
        intent.putExtra(TeamDetailActivity.EXTRA_USER, team);
        if (Build.VERSION.SDK_INT >= 21) {
            View sharedView = v.findViewById(R.id.iv_avatar);
            String transitionName = getString(R.string.image_team_avatar);

            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);

            startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            startActivity(intent);
        }*/
    }

    @Override
    public void setPresenter(@NonNull TeamsContract.Presenter presenter) {
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

    private void loadTeams() {
        mPresenter.loadTeams();
    }

    private void loadMore(int page) {
        mPresenter.loadMore(page);
    }
}
