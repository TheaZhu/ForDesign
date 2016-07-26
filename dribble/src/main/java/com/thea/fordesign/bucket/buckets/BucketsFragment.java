package com.thea.fordesign.bucket.buckets;


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
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.databinding.BucketItemBinding;
import com.thea.fordesign.databinding.BucketsFragBinding;
import com.thea.fordesign.util.LogUtil;
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
 * Use the {@link BucketsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BucketsFragment extends BaseDataBindingFragment<BucketsFragBinding> implements
        BucketsContract.View {
    public static final String TAG = BucketsFragment.class.getSimpleName();

    private BucketsContract.Presenter mPresenter;
    private BucketAdapter mAdapter;

    public BucketsFragment() {
    }

    public static BucketsFragment newInstance() {
        return new BucketsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buckets;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBinding.srlBuckets.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);
        mViewDataBinding.srlBuckets.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener
                () {
            @Override
            public void onRefresh() {
                mPresenter.loadBuckets();
            }
        });

        RecyclerView recyclerView = mViewDataBinding.rvBuckets;
        mAdapter = new BucketAdapter(new ArrayList<DribbbleBucket>(), mPresenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new FooterWrapAdapter(mAdapter, createLoadingView(recyclerView)));
        recyclerView.addOnScrollListener(new LoadMoreListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LogUtil.i(TAG, "current page: " + currentPage);
                mPresenter.loadMore(currentPage);
            }
        });

        mPresenter.loadBuckets();
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
        SwipeRefreshLayout srl = mViewDataBinding.srlBuckets;
        if (srl == null || srl.isRefreshing() == active) {
            return;
        }
        Observable.just(active).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        mViewDataBinding.srlBuckets.setRefreshing(active);
                    }
                });
    }

    @Override
    public void showBuckets(List<DribbbleBucket> buckets) {
        mAdapter.replaceData(buckets);
    }

    @Override
    public void insertBuckets(List<DribbbleBucket> buckets) {
        mAdapter.insertData(buckets);
    }

    @Override
    public void showBucketShotsUi(int bucketId) {

    }

    @Override
    public void setPresenter(@NonNull BucketsContract.Presenter presenter) {
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

    private class BucketAdapter extends RecyclerView.Adapter<BucketsFragment.BucketViewHolder> {
        private List<DribbbleBucket> mBuckets;

        private BucketsContract.Presenter mUserActionsListener;

        public BucketAdapter(List<DribbbleBucket> buckets, BucketsContract.Presenter itemListener) {
            mBuckets = buckets;
            mUserActionsListener = itemListener;
        }

        public void replaceData(List<DribbbleBucket> buckets) {
            mBuckets = buckets;
            notifyDataSetChanged();
        }

        public void insertData(List<DribbbleBucket> buckets) {
            int start = mBuckets.size();
            mBuckets.addAll(buckets);
            notifyItemRangeInserted(start, buckets.size());
        }

        @Override
        public BucketsFragment.BucketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BucketItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from
                    (parent.getContext()), R.layout.card_bucket, parent, false);
            return new BucketsFragment.BucketViewHolder(viewDataBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(BucketsFragment.BucketViewHolder holder, int position) {
            BucketItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
            DribbbleBucket bucket = mBuckets.get(position);
            viewDataBinding.setBucket(bucket);
            viewDataBinding.setActionHandler(mUserActionsListener);
            viewDataBinding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mBuckets.size();
        }
    }

    static class BucketViewHolder extends RecyclerView.ViewHolder {

        BucketViewHolder(View itemView) {
            super(itemView);
        }
    }
}
