package com.thea.fordesign.user.followers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.FollowersActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class FollowersActivity extends BaseDataBindingActivity<FollowersActBinding> {
    public static final String TAG = FollowersActivity.class.getSimpleName();
    public static final String EXTRA_TITLE = "activity_title";
    public static final String EXTRA_FOLLOWER_URL = "follower_url";
    public static final String EXTRA_ITEM_TYPE = "item_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra(EXTRA_TITLE));

        if (intent.hasExtra(EXTRA_FOLLOWER_URL)) {
            String mFollowerUrl = intent.getStringExtra(EXTRA_FOLLOWER_URL);
            int mType = intent.getIntExtra(EXTRA_ITEM_TYPE, FollowersFragment.TYPE_FOLLOWER);
            FollowersFragment followersFragment = (FollowersFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fl_content);
            if (followersFragment == null) {
                followersFragment = FollowersFragment.newInstance(mFollowerUrl, mType, false);
                ActivityUtil.addFragmentToActivity(
                        getSupportFragmentManager(), followersFragment, R.id.fl_content);
            }
            new FollowersPresenter(followersFragment, new UserModel(this));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_followers;
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
