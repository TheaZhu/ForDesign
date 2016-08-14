package com.thea.fordesign.shot.detail;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.databinding.ShotImageFragBinding;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShotImageFragment extends BaseDataBindingFragment<ShotImageFragBinding> implements ShotImageContract.View {
    public static final String TAG = ShotImageFragment.class.getSimpleName();
    public static final String ARG_SHOT_IMAGE_URL = "shot_image_url";

    private String mUrl;

    private ShotImageContract.Presenter mPresenter;
    private PhotoViewAttacher attacher;

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
        mViewDataBinding.setActionHandler(mPresenter);
        Glide.with(ShotImageFragment.this)
                .load(mUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .dontAnimate()
                .into(new GlideDrawableImageViewTarget(mViewDataBinding.ivShotImage) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        attacher = new PhotoViewAttacher(mViewDataBinding.ivShotImage);
                    }
                });

        mPresenter.start();
    }

    @Override
    public void showPrevious() {
        getActivity().onBackPressed();
    }

    @Override
    public void showShareChooser(@NonNull File file) {
        LogUtil.i(TAG, "image url: " + mUrl);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
        } else {
            shareIntent.putExtra(Intent.EXTRA_TEXT, mUrl);
            shareIntent.setType("text/plain");
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.title_send_to)));
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
