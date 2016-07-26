package com.thea.fordesign.project.projects;


import android.databinding.DataBindingUtil;
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
import android.widget.TextView;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleProject;
import com.thea.fordesign.databinding.ProjectItemBinding;
import com.thea.fordesign.databinding.ProjectsFragBinding;
import com.thea.fordesign.util.Preconditions;
import com.thea.fordesign.widget.FooterWrapAdapter;
import com.thea.fordesign.widget.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectsFragment extends BaseDataBindingFragment<ProjectsFragBinding> implements
        ProjectsContract.View {
    private ProjectsContract.Presenter mPresenter;
    private ProjectAdapter mAdapter;

    public ProjectsFragment() {
    }

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_projects;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBinding.srlProjects.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);
        mViewDataBinding.srlProjects.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener
                () {
            @Override
            public void onRefresh() {
                mPresenter.loadProjects();
            }
        });

        RecyclerView recyclerView = mViewDataBinding.rvProjects;
        mAdapter = new ProjectAdapter(new ArrayList<DribbbleProject>(), mPresenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new FooterWrapAdapter(mAdapter, createLoadingView(recyclerView)));
        recyclerView.addOnScrollListener(new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.loadMore(currentPage);
            }
        });

        mPresenter.loadProjects();
    }

    private View createLoadingView(ViewGroup parent) {
        View loadingView = LayoutInflater.from(getContext()).inflate(R.layout.loading_layout,
                parent, false);
        TextView tv = (TextView) loadingView.findViewById(R.id.tv_loading_message);
        tv.setText(R.string.loading);

        return loadingView;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        final SwipeRefreshLayout srl = mViewDataBinding.srlProjects;
        if (srl == null || srl.isRefreshing() == active) {
            return;
        }
        Observable.just(active).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        mViewDataBinding.srlProjects.setRefreshing(active);
                    }
                });
    }

    @Override
    public void showProjects(List<DribbbleProject> projects) {
        mAdapter.replaceData(projects);
    }

    @Override
    public void insertProjects(List<DribbbleProject> projects) {
        mAdapter.insertData(projects);
    }

    @Override
    public void showProjectShotsUi(int projectId) {

    }

    @Override
    public void setPresenter(@NonNull ProjectsContract.Presenter presenter) {
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

    private class ProjectAdapter extends RecyclerView.Adapter<ProjectsFragment.ProjectViewHolder> {
        private List<DribbbleProject> mProjects;

        private ProjectsContract.Presenter mUserActionsListener;

        public ProjectAdapter(List<DribbbleProject> projects, ProjectsContract.Presenter itemListener) {
            mProjects = projects;
            mUserActionsListener = itemListener;
        }

        public void replaceData(List<DribbbleProject> projects) {
            mProjects = projects;
            notifyDataSetChanged();
        }

        public void insertData(List<DribbbleProject> projects) {
            int start = mProjects.size();
            mProjects.addAll(projects);
            notifyItemRangeInserted(start, projects.size());
        }

        @Override
        public ProjectsFragment.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ProjectItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from
                    (parent.getContext()), R.layout.card_project, parent, false);
            return new ProjectsFragment.ProjectViewHolder(viewDataBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(ProjectsFragment.ProjectViewHolder holder, int position) {
            ProjectItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
            DribbbleProject project = mProjects.get(position);
            viewDataBinding.setProject(project);
            viewDataBinding.setActionHandler(mUserActionsListener);
            viewDataBinding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mProjects.size();
        }
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {

        ProjectViewHolder(View itemView) {
            super(itemView);
        }
    }
}
