package com.thea.fordesign.user.followers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thea.fordesign.R;
import com.thea.fordesign.util.ActivityUtil;

public class MyFollowersActivity extends AppCompatActivity {
    public static final String TAG = MyFollowersActivity.class.getSimpleName();
    public static final String EXTRA_FOLLOWER_URL = "follower_url";
    public static final String EXTRA_ITEM_TYPE = "item_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_followers);

        Intent intent = getIntent();
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
            if (mType == FollowersFragment.TYPE_FOLLOWER)
                new FollowersPresenter(followersFragment);
            else
                new FollowingsPresenter(followersFragment);

        }
    }
}
