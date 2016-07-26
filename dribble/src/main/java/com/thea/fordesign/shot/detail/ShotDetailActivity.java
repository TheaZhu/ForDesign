package com.thea.fordesign.shot.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.ShotDetailActBinding;
import com.thea.fordesign.util.ActivityUtil;
import com.thea.fordesign.util.StringUtil;

public class ShotDetailActivity extends BaseDataBindingActivity<ShotDetailActBinding> {
    public static final String TAG = ShotDetailActivity.class.getSimpleName();

    public static final String EXTRA_SHOT_ID = "shot_id";
    public static final String EXTRA_SHOT_IMAGE_URL = "shot_image_url";
    public static final String EXTRA_SHOT = "dribbble_shot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ShotDetailFragment fragment = (ShotDetailFragment) getSupportFragmentManager()
                .findFragmentByTag(ShotDetailFragment.TAG);
        if (fragment == null) {
            fragment = ShotDetailFragment.newInstance();
        }
        new ShotDetailPresenter(getIntent().getIntExtra(EXTRA_SHOT_ID, -1), fragment, new
                UserModel(this));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nsv_shot_detail, fragment, ShotDetailFragment.TAG);
        transaction.commit();

        String url = getIntent().getStringExtra(EXTRA_SHOT_IMAGE_URL);
        if (StringUtil.isGif(url)) {
            displayGifShot(url);
        } else {
            displayBitmapShot(url);
        }
//        Glide.with(ShotDetailActivity.this)
//                .load(getIntent().getStringExtra(EXTRA_SHOT_IMAGE_URL))
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .centerCrop()
//                .into(mViewDataBinding.ivShot);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shot_detail;
    }

    public void displayBitmapShot(String url) {
        Glide.with(ShotDetailActivity.this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mViewDataBinding.ivShot);
    }

    public void displayGifShot(String url) {
        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(mViewDataBinding
                .ivShot, 3);
        Glide.with(ShotDetailActivity.this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(target);
    }
}
