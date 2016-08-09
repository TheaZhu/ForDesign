package com.thea.fordesign.shot.detail;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.ShotDetailActBinding;

public class ShotDetailActivity extends BaseDataBindingActivity<ShotDetailActBinding> {
    public static final String TAG = ShotDetailActivity.class.getSimpleName();

    public static final String EXTRA_SHOT_ID = "shot_id";
    public static final String EXTRA_SHOT_IMAGE_URL = "shot_image_url";
    public static final String EXTRA_SHOT = "dribbble_shot";

    private String mImageUrl;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityUtil.setupToolbar(this, R.id.toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mImageUrl = getIntent().getStringExtra(EXTRA_SHOT_IMAGE_URL);
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

        Glide.with(ShotDetailActivity.this)
                .load(mImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .placeholder(R.mipmap.default_shot)
                .into(mViewDataBinding.ivShot);

        mViewDataBinding.ivShot.setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showImageFullScreen();
        }
    };

    private void showImageFullScreen() {
        ShotImageFragment fragment = (ShotImageFragment) getSupportFragmentManager()
                .findFragmentByTag(ShotImageFragment.TAG);
        if (fragment == null) {
            fragment = ShotImageFragment.newInstance(mImageUrl);
            new ShotImagePresenter(fragment, mImageUrl);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.rl_content, fragment, ShotImageFragment.TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shot_detail;
    }

}
