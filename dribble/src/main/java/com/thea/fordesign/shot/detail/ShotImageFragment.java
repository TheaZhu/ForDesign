package com.thea.fordesign.shot.detail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.databinding.ShotImageFragBinding;
import com.thea.fordesign.util.Preconditions;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShotImageFragment extends BaseDataBindingFragment<ShotImageFragBinding> implements ShotImageContract.View {
    public static final String TAG = ShotImageFragment.class.getSimpleName();
    public static final String ARG_SHOT_IMAGE_URL = "shot_image_url";

    private String mUrl;

    private ShotImageContract.Presenter mPresenter;

    public ShotImageFragment() {
    }

    public static ShotImageFragment newInstance(String url) {
        ShotImageFragment fragment = new ShotImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SHOT_IMAGE_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_SHOT_IMAGE_URL);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shot_image;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Glide.with(ShotImageFragment.this)
                .load(mUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .into(mViewDataBinding.ivShotImage);
        PhotoViewAttacher attacher = new PhotoViewAttacher(mViewDataBinding.ivShotImage);
        mPresenter.start();
    }

    @Override
    public void showPrevious() {
        getActivity().onBackPressed();
    }

    @Override
    public void setPresenter(@NonNull ShotImageContract.Presenter presenter) {
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
