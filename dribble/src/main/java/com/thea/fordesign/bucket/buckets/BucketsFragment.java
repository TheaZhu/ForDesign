package com.thea.fordesign.bucket.buckets;


import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.thea.fordesign.DribbbleConstant;
import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.databinding.BucketItemBinding;
import com.thea.fordesign.databinding.BucketsFragBinding;
import com.thea.fordesign.shot.shots.ShotsActivity;
import com.thea.fordesign.util.ActivityUtil;
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
 * Use the {@link BucketsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BucketsFragment extends BaseDataBindingFragment<BucketsFragBinding> implements
        BucketsContract.View {
    public static final String TAG = BucketsFragment.class.getSimpleName();
    public static final String ARG_HAS_MENU = "has_menu";

    private boolean hasMenu;

    private BucketsContract.Presenter mPresenter;
    private MyLoadingView mLoadingView;
    private BucketAdapter mAdapter;
    private LoadMoreListener mLoadMoreListener;

    public BucketsFragment() {
    }

    public static BucketsFragment newInstance(boolean hasMenu) {
        BucketsFragment fragment = new BucketsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_HAS_MENU, hasMenu);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hasMenu = getArguments().getBoolean(ARG_HAS_MENU, false);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (hasMenu)
            inflater.inflate(R.menu.menu_buckets, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_bucket:
                showAddDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buckets;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(hasMenu);
        mViewDataBinding.srlBuckets.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);
        mViewDataBinding.srlBuckets.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener
                () {
            @Override
            public void onRefresh() {
                mPresenter.loadBuckets();
                mLoadMoreListener.reset();
            }
        });

        final RecyclerView recyclerView = mViewDataBinding.rvBuckets;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new BucketAdapter(new ArrayList<DribbbleBucket>(), mPresenter);
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

        mPresenter.loadBuckets();
    }

    @Override
    public void setRefreshingIndicator(final boolean active) {
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
    public void setLoadingIndicator(boolean visible, boolean active, @StringRes int resId,
                                    boolean enableClick) {
        RecyclerView.Adapter adapter = mViewDataBinding.rvBuckets.getAdapter();
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
    public void showBuckets(List<DribbbleBucket> buckets) {
        mAdapter.replaceData(buckets);
    }

    @Override
    public void insertBuckets(List<DribbbleBucket> buckets) {
        mAdapter.insertData(buckets);
    }

    @Override
    public void insertBucket(DribbbleBucket bucket) {
        mAdapter.insertItem(bucket);
    }

    @Override
    public void showBucketShotsUi(int bucketId) {
        Intent intent = new Intent(getContext(), ShotsActivity.class);
        intent.putExtra(ShotsActivity.EXTRA_TITLE, getString(R.string.title_bucket_shots));
        intent.putExtra(ShotsActivity.EXTRA_SHOTS_URL, DribbbleConstant.BASE_URL + "buckets/" +
                bucketId + "/shots");
        startActivity(intent);
    }

    @Override
    public void showBucketEditDialog(final int bucketId, String name, String description) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_bucket, null);
        final EditText etName = (EditText) view.findViewById(R.id.et_bucket_name);
        final EditText etDescription = (EditText) view.findViewById(R.id.et_bucket_description);
        etName.setText(name);
        etDescription.setText(description);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_edit_bucket)
                .setView(view)
                .setPositiveButton(R.string.btn_update_bucket, new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.updateBucket(bucketId, etName.getText().toString(), etDescription
                                .getText().toString());
                    }
                })
                .setNegativeButton(R.string.btn_cancel, null).create();

        ActivityUtil.setupDialogButtonTextColor(dialog);

        dialog.show();
    }

    @Override
    public void showBucketDeleteDialog(final int bucketId) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_delete_bucket)
                .setMessage(R.string.msg_delete_bucket)
                .setPositiveButton(R.string.btn_delete_bucket, new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.deleteBucket(bucketId);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, null).create();

        ActivityUtil.setupDialogButtonTextColor(dialog);

        dialog.show();
    }

    private void showAddDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_bucket, null);
        final EditText etName = (EditText) view.findViewById(R.id.et_bucket_name);
        final EditText etDescription = (EditText) view.findViewById(R.id.et_bucket_description);
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_create_bucket)
                .setView(view)
                .setPositiveButton(R.string.btn_create_bucket, new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.createNewBucket(etName.getText().toString(), etDescription
                                .getText().toString());
                    }
                }).setNegativeButton(R.string.btn_cancel, null).create();

        ActivityUtil.setupDialogButtonTextColor(dialog);

        dialog.show();
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

        public void insertItem(DribbbleBucket bucket) {
            mBuckets.add(0, bucket);
            notifyItemInserted(0);
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
