package com.thea.fordesign.shot.detail;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.ShotDetailFragBinding;
import com.thea.fordesign.like.shot.ShotLikesActivity;
import com.thea.fordesign.user.detail.UserDetailActivity;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShotDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShotDetailFragment extends BaseDataBindingFragment<ShotDetailFragBinding> implements
        ShotDetailContract.View {
    public static final String TAG = ShotDetailFragment.class.getSimpleName();

    private ShotDetailContract.Presenter mPresenter;

    public ShotDetailFragment() {
    }

    public static ShotDetailFragment newInstance() {
        return new ShotDetailFragment();
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
        inflater.inflate(R.menu.menu_shot_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.action_share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shot_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
    }

    @Override
    public void showShot(DribbbleShot shot) {
        mViewDataBinding.setShot(shot);
        mViewDataBinding.setActionHandler(mPresenter);

        LogUtil.i(TAG, shot.toString());
        Glide.with(ShotDetailFragment.this)
                .load(shot.getUser().getAvatarUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .placeholder(R.mipmap.default_user_avatar)
                .into(mViewDataBinding.cvDesigner.ivAvatar);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    public void showShotLiked() {

    }

    @Override
    public void showShotDisliked() {

    }

    @Override
    public void showUserDetailsUi(@NonNull DribbbleUser user, View v) {
        Intent intent = new Intent(getContext(), UserDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(UserDetailActivity.EXTRA_USER, user);
//        intent.putExtra(UserDetailActivity.EXTRA_AVATAR_URL, (String) sharedView.getTag());
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
    public void showLikesUi(@NonNull String likesUrl) {
        Intent intent = new Intent(getContext(), ShotLikesActivity.class);
        intent.putExtra(ShotLikesActivity.EXTRA_LIKE_URL, likesUrl);
        startActivity(intent);
    }

    @Override
    public void setPresenter(@NonNull ShotDetailContract.Presenter presenter) {
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
