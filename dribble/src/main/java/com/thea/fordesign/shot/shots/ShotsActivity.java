package com.thea.fordesign.shot.shots;

import android.content.Intent;
import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.ShotsActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class ShotsActivity extends BaseDataBindingActivity<ShotsActBinding> {
    public static final String TAG = ShotsActivity.class.getSimpleName();
    public static final String EXTRA_TITLE = "activity_title";
    public static final String EXTRA_SHOTS_URL = "shots_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra(EXTRA_TITLE));


        if (intent.hasExtra(EXTRA_SHOTS_URL)) {
            String mShotUrl = intent.getStringExtra(EXTRA_SHOTS_URL);
            ShotsFragment shotsFragment = (ShotsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fl_content);
            if (shotsFragment == null) {
                shotsFragment = ShotsFragment.newInstance(mShotUrl, false);
                ActivityUtil.addFragmentToActivity(
                        getSupportFragmentManager(), shotsFragment, R.id.fl_content);
            }
            new ShotsPresenter(shotsFragment, new UserModel(this));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shots;
    }

}
