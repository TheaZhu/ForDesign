package com.thea.fordesign.bucket.selectable;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.EditText;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.databinding.ShotToBucketsFragBinding;
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
 * Use the {@link ShotToBucketsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShotToBucketsFragment extends BaseDataBindingFragment<ShotToBucketsFragBinding>
        implements ShotToBucketsContract.View {
    public static final String TAG = ShotToBucketsFragment.class.getSimpleName();

    private ShotToBucketsContract.Presenter mPresenter;

    private SelectableBucketAdapter mAdapter;
    private LoadMoreListener mLoadMoreListener;
    private MyLoadingView mLoadingView;

    public ShotToBucketsFragment() {
    }

    public static ShotToBucketsFragment newInstance() {
        return new ShotToBucketsFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
        return R.layout.fragment_shot_to_buckets;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
        mAdapter = new SelectableBucketAdapter(new ArrayList<DribbbleBucket>(), mPresenter);
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
    public void showItemSelected(View v, boolean selected) {
        if (selected == v.isSelected())
            return;
        v.setSelected(selected);

        View rl = v.findViewById(R.id.rl_bucket);
        rl.setSelected(selected);

        View iv = v.findViewById(R.id.iv_select_indicator);
        iv.setVisibility(selected ? View.VISIBLE : View.GONE);

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
    public void setPresenter(@NonNull ShotToBucketsContract.Presenter presenter) {
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
