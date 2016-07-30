package com.thea.fordesign.bucket.buckets;

import android.content.Intent;
import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.BucketsActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class BucketsActivity extends BaseDataBindingActivity<BucketsActBinding> {
    public static final String EXTRA_TITLE = "activity_title";
    public static final String EXTRA_BUCKETS_URL = "buckets_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra(EXTRA_TITLE));
        
        if (intent.hasExtra(EXTRA_BUCKETS_URL)) {
            String mBucketUrl = intent.getStringExtra(EXTRA_BUCKETS_URL);
            BucketsFragment bucketsFragment = (BucketsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fl_content);
            if (bucketsFragment == null) {
                bucketsFragment = BucketsFragment.newInstance();
                ActivityUtil.addFragmentToActivity(
                        getSupportFragmentManager(), bucketsFragment, R.id.fl_content);
            }
            new BucketsPresenter(mBucketUrl, bucketsFragment, new UserModel(this));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buckets;
    }

}
