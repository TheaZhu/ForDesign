package com.thea.fordesign.shot.shots;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.databinding.ShotItemBinding;
import com.thea.fordesign.databinding.ShotsFragmentBinding;
import com.thea.fordesign.shot.detail.ShotDetailActivity;
import com.thea.fordesign.user.detail.UserDetailActivity;
import com.thea.fordesign.util.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShotsFragment extends BaseDataBindingFragment<ShotsFragmentBinding> implements
        ShotsContract.View {
    public static final String TAG = ShotsFragment.class.getSimpleName();

    private ShotsContract.Presenter mPresenter;

    private ShotAdapter mAdapter;

    public ShotsFragment() {
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
        return R.layout.shots_fragment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBinding.srlShots.setColorSchemeResources(R.color.dribbble_pink, R.color
                .dribbble_link_blue, R.color.dribbble_playbook);

        RecyclerView recyclerView = mViewDataBinding.rvShots;
        mAdapter = new ShotAdapter(new ArrayList<DribbbleShot>(), mPresenter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener) {
        mViewDataBinding.srlShots.setOnRefreshListener(refreshListener);
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
    public void showShotDetailsUi(int shotId) {
        Intent intent = new Intent(getContext(), ShotDetailActivity.class);
        intent.putExtra(ShotDetailActivity.EXTRA_SHOT_ID, shotId);
        startActivity(intent);
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

    @Override
    protected void setData() {
    }

    public static class ShotAdapter extends RecyclerView.Adapter<ShotViewHolder> {
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

        @Override
        public ShotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ShotItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent
                    .getContext()), R.layout.shot_item, parent, false);
            return new ShotViewHolder(viewDataBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(ShotViewHolder holder, int position) {
            ShotItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
            ShotItemActionHandler actionHandler = new ShotItemActionHandler(mUserActionsListener);
            DribbbleShot shot = mShots.get(position);
            viewDataBinding.setShot(shot);
            viewDataBinding.setActionHandler(actionHandler);

            Context context = holder.itemView.getContext();
//            viewDataBinding.ivShot.setImageResource(R.mipmap.default_shot);
//            viewDataBinding.ivAvatar.setImageResource(R.mipmap.ic_dribbble_square);

            Glide.with(context)
                    .load(shot.getUser().getAvatarUrl())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_dribbble_square)
                    .crossFade()
                    .into(viewDataBinding.ivAvatar);

            Glide.with(context)
                    .load(shot.getImages().getNormal())
                    .centerCrop()
                    .placeholder(R.mipmap.default_shot)
                    .crossFade()
                    .into(viewDataBinding.ivShot);

            viewDataBinding.tvLikesCount.setCompoundDrawablesWithIntrinsicBounds(R.mipmap
                    .ic_like_inactive, 0, 0, 0);
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
