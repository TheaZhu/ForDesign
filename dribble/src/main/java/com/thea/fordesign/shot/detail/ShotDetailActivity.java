package com.thea.fordesign.shot.detail;

import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeImageTransform;
import android.transition.Slide;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.ShotDetailActBinding;

import static android.view.Gravity.BOTTOM;

public class ShotDetailActivity extends BaseDataBindingActivity<ShotDetailActBinding> {
    public static final String TAG = ShotDetailActivity.class.getSimpleName();

    public static final String EXTRA_SHOT_ID = "shot_id";
    public static final String EXTRA_SHOT_IMAGE_URL = "shot_image_url";
    public static final String EXTRA_USER = "shot_user";
    public static final String EXTRA_SHOT = "dribbble_shot";
    public static final String EXTRA_TOOLBAR_STYLE = "toolbar_style";

    private String mImageUrl;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar;
        toolbar = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_transparent,
                mViewDataBinding.rlContent, false);
        mViewDataBinding.rlContent.addView(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mImageUrl = getIntent().getStringExtra(EXTRA_SHOT_IMAGE_URL);
        ShotDetailFragment fragment = (ShotDetailFragment) getSupportFragmentManager()
                .findFragmentByTag(ShotDetailFragment.TAG);
        if (fragment == null) {
            fragment = ShotDetailFragment.newInstance();
        }
        DribbbleUser user = null;
        if (getIntent().hasExtra(EXTRA_USER)) {
            user = getIntent().getParcelableExtra(EXTRA_USER);
        }
        new ShotDetailPresenter(getIntent().getIntExtra(EXTRA_SHOT_ID, -1), user, fragment, new
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fragment.setSharedElementEnterTransition(new ChangeImageTransform());
                fragment.setEnterTransition(new Slide(BOTTOM));
                fragment.setSharedElementReturnTransition(new ChangeImageTransform());
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.addSharedElement(mViewDataBinding.ivShot, getString(R.string.image_shot));
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
