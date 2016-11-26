package com.thea.fordesign.bucket.selectable;

import android.content.Intent;
import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.ShotToBucketsActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class ShotToBucketsActivity extends BaseDataBindingActivity<ShotToBucketsActBinding> {
    public static final String EXTRA_SHOT_ID = "shot_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ShotToBucketsFragment bucketsFragment = (ShotToBucketsFragment)
                getSupportFragmentManager().findFragmentById(R.id.fl_content);
        if (bucketsFragment == null) {
            bucketsFragment = ShotToBucketsFragment.newInstance();
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), bucketsFragment, R.id.fl_content);
        }
        new ShotToBucketsPresenter(bucketsFragment, intent.getIntExtra(EXTRA_SHOT_ID, 0), new
                UserModel(this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shot_to_buckets;
    }
}
