package com.thea.fordesign.shot.shots;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.MyShotsActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class MyShotsActivity extends BaseDataBindingActivity<MyShotsActBinding> {
    public static final String TAG = MyShotsActivity.class.getSimpleName();
    public static final String EXTRA_SHOTS_URL = "shots_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
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
        return R.layout.activity_my_shots;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
