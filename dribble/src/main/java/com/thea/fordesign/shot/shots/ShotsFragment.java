package com.thea.fordesign.shot.shots;


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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.DribbleConstant;
import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.databinding.LaconicShotItemBinding;
import com.thea.fordesign.databinding.ShotsFragBinding;
import com.thea.fordesign.shot.detail.ShotDetailActivity;
import com.thea.fordesign.user.detail.UserDetailActivity;
import com.thea.fordesign.util.Preconditions;
import com.thea.fordesign.widget.LoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShotsFragment extends BaseDataBindingFragment<ShotsFragBinding> implements
        ShotsContract.View {
    public static final String TAG = ShotsFragment.class.getSimpleName();
    public static final String ARG_SHOT_URL = "shot_url";
    public static final String ARG_CHOOSE_TYPE = "choose_shot_type";

    private String mShotsUrl;
    private boolean mCanChooseType = true;

    private String mListType;
    private String mTimeFrameType;
    private String mSortType;

    private MenuItem mListMenuItem;
    private MenuItem mSortListMenuItem;
    private MenuItem mTimeFrameMenuItem;

    private ShotsContract.Presenter mPresenter;

    private ShotAdapter mAdapter;
    private LoadMoreListener mLoadMoreListener;

    public ShotsFragment() {
        mListType = DribbleConstant.SHOT_LIST_DEFAULT;
        mSortType = DribbleConstant.SHOT_SORT_DEFAULT;
        mTimeFrameType = DribbleConstant.SHOT_TIME_FRAME_DEFAULT;
    }

    public static ShotsFragment newInstance() {
        return new ShotsFragment();
    }

    public static ShotsFragment newInstance(String shotsUrl, boolean canChooseType) {
        ShotsFragment fragment = new ShotsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SHOT_URL, shotsUrl);
        bundle.putBoolean(ARG_CHOOSE_TYPE, canChooseType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShotsUrl = getArguments().getString(ARG_SHOT_URL);
            mCanChooseType = getArguments().getBoolean(ARG_CHOOSE_TYPE, true);
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
        if (mCanChooseType) {
            inflater.inflate(R.menu.menu_shots_fragment, menu);
            mListMenuItem = menu.findItem(R.id.menu_list_type);
            mSortListMenuItem = menu.findItem(R.id.menu_sort_type);
            mTimeFrameMenuItem = menu.findItem(R.id.menu_time_frame);
            initShotsType();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_type:
                showListPopUpMenu();
                break;
            case R.id.menu_sort_type:
                showSortPopUpMenu();
                break;
            case R.id.menu_time_frame:
                showTimeFramePopUpMenu();
                break;
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shots;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        if (mCanChooseType)
            setHasOptionsMenu(true);
        mViewDataBinding.srlShots.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);
        mViewDataBinding.srlShots.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadShots();
            }
        });

        RecyclerView recyclerView = mViewDataBinding.rvShots;
        mAdapter = new ShotAdapter(new ArrayList<DribbbleShot>(), mPresenter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mLoadMoreListener = new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (mShotsUrl != null)
                    mPresenter.loadMore(mShotsUrl, currentPage);
                else
                    mPresenter.loadMore(mListType, mSortType, mTimeFrameType, currentPage);
            }
        };
        recyclerView.addOnScrollListener(mLoadMoreListener);

        loadShots();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        final SwipeRefreshLayout srl = mViewDataBinding.srlShots;
        if (srl == null || srl.isRefreshing() == active) {
            return;
        }

        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showShots(List<DribbbleShot> shots) {
        mAdapter.replaceData(shots);
    }

    @Override
    public void insertShots(List<DribbbleShot> shots) {
        mAdapter.insertData(shots);
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
    public void showUserDetailsUi(int userId) {
        Intent intent = new Intent(getContext(), UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.EXTRA_USER_ID, userId);
        startActivity(intent);
    }

    @Override
    public void setPresenter(@NonNull ShotsContract.Presenter presenter) {
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

    private void loadShots() {
        if (mShotsUrl != null)
            mPresenter.loadShots(mShotsUrl);
        else
            mPresenter.loadShots(mListType, mSortType, mTimeFrameType);
    }

    private void changeListType(String list) {
        if (!list.equalsIgnoreCase(mListType)) {
            mListType = list;
            mListMenuItem.setTitle(mListType.substring(0, 3).toUpperCase());
            if (mLoadMoreListener != null)
                mLoadMoreListener.reset();
            mPresenter.loadShots(mListType, mSortType, mTimeFrameType);
        }
    }

    private void changeSortType(String sort) {
        if (!sort.equalsIgnoreCase(mSortType)) {
            mSortType = sort;
            mSortListMenuItem.setTitle(mSortType.substring(0, 3).toUpperCase());
            if (mLoadMoreListener != null)
                mLoadMoreListener.reset();
            mPresenter.loadShots(mListType, mSortType, mTimeFrameType);
        }
    }

    private void changeTimeFrameType(String timeFrame) {
        if (!timeFrame.equalsIgnoreCase(mTimeFrameType)) {
            mTimeFrameType = timeFrame;
            mTimeFrameMenuItem.setTitle(mTimeFrameType.substring(0, 3).toUpperCase());
            if (mLoadMoreListener != null)
                mLoadMoreListener.reset();
            mPresenter.loadShots(mListType, mSortType, mTimeFrameType);
        }
    }

    private void initShotsType() {
        mListMenuItem.setTitle(mListType.substring(0, 3).toUpperCase());
        mSortListMenuItem.setTitle(mSortType.substring(0, 3).toUpperCase());
        mTimeFrameMenuItem.setTitle(mTimeFrameType.substring(0, 3).toUpperCase());
    }

    private void showListPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id
                .menu_list_type));
        popup.getMenuInflater().inflate(R.menu.menu_list_type, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                String list = mListType;
                switch (item.getItemId()) {
                    case R.id.action_any:
                        list = DribbleConstant.SHOT_LIST_ANY;
                        break;
                    case R.id.action_debuts:
                        list = DribbleConstant.SHOT_LIST_DEBUT;
                        break;
                    case R.id.action_teams:
                        list = DribbleConstant.SHOT_LIST_TEAM;
                        break;
                    case R.id.action_playoffs:
                        list = DribbleConstant.SHOT_LIST_PLAYOFF;
                        break;
                    case R.id.action_rebounds:
                        list = DribbleConstant.SHOT_LIST_REBOUND;
                        break;
                    case R.id.action_animated:
                        list = DribbleConstant.SHOT_LIST_ANIMATED;
                        break;
                    case R.id.action_attachments:
                        list = DribbleConstant.SHOT_LIST_ATTACHMENT;
                        break;
                }
                changeListType(list);
                return true;
            }
        });
        popup.show();
    }

    private void showSortPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id
                .menu_sort_type));
        popup.getMenuInflater().inflate(R.menu.menu_sort_type, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_popularity:
                        changeSortType(DribbleConstant.SHOT_SORT_POPULARITY);
                        break;
                    case R.id.action_recent:
                        changeSortType(DribbleConstant.SHOT_SORT_RECENT);
                        break;
                    case R.id.action_views:
                        changeSortType(DribbleConstant.SHOT_SORT_VIEWS);
                        break;
                    case R.id.action_comments:
                        changeSortType(DribbleConstant.SHOT_SORT_COMMENTS);
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    private void showTimeFramePopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id
                .menu_time_frame));
        popup.getMenuInflater().inflate(R.menu.menu_time_frame, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recent:
                        changeTimeFrameType(DribbleConstant.SHOT_TIME_FRAME_RECENT);
                        break;
                    case R.id.action_week:
                        changeTimeFrameType(DribbleConstant.SHOT_TIME_FRAME_WEEK);
                        break;
                    case R.id.action_month:
                        changeTimeFrameType(DribbleConstant.SHOT_TIME_FRAME_MONTH);
                        break;
                    case R.id.action_year:
                        changeTimeFrameType(DribbleConstant.SHOT_TIME_FRAME_YEAR);
                        break;
                    case R.id.action_ever:
                        changeTimeFrameType(DribbleConstant.SHOT_TIME_FRAME_EVER);
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    public class ShotAdapter extends RecyclerView.Adapter<ShotViewHolder> {
        private List<DribbbleShot> mShots;

        private ShotsContract.Presenter mUserActionsListener;

        public ShotAdapter(List<DribbbleShot> shots, ShotsContract.Presenter itemListener) {
            mShots = shots;
            mUserActionsListener = itemListener;
        }

        public void replaceData(List<DribbbleShot> shots) {
            mShots = shots;
            notifyDataSetChanged();
        }

        public void insertData(List<DribbbleShot> shots) {
            int start = mShots.size();
            mShots.addAll(shots);
            notifyItemRangeInserted(start, shots.size());
        }

        @Override
        public ShotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LaconicShotItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent
                    .getContext()), R.layout.card_shot_without_infos, parent, false);
            return new ShotViewHolder(viewDataBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(ShotViewHolder holder, int position) {
            LaconicShotItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
            ShotItemActionHandler actionHandler = new ShotItemActionHandler(mUserActionsListener);
            DribbbleShot shot = mShots.get(position);
            viewDataBinding.setShot(shot);
            viewDataBinding.setActionHandler(actionHandler);

            Glide.with(ShotsFragment.this)
                    .load(shot.getImages().getNormal())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .placeholder(R.mipmap.default_shot)
                    .crossFade()
                    .into(viewDataBinding.ivShot);

//            viewDataBinding.tvLikesCount.setCompoundDrawablesWithIntrinsicBounds(R.mipmap
//                    .ic_like_inactive_small, 0, 0, 0);
            viewDataBinding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mShots.size();
        }
    }

    public static class ShotViewHolder extends RecyclerView.ViewHolder {

        public ShotViewHolder(View itemView) {
            super(itemView);
        }
    }
}
