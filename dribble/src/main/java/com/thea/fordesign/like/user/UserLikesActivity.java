package com.thea.fordesign.like.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.UserLikesActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class UserLikesActivity extends BaseDataBindingActivity<UserLikesActBinding> {
    public static final String EXTRA_TITLE = "activity_title";
    public static final String EXTRA_LIKE_URL = "like_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra(EXTRA_TITLE));

        UserLikesFragment fragment = (UserLikesFragment) getSupportFragmentManager()
                .findFragmentByTag(UserLikesFragment.TAG);
        if (fragment == null) {
            fragment = UserLikesFragment.newInstance(intent.getStringExtra(EXTRA_LIKE_URL));
        }
        new UserLikesPresenter(fragment, new UserModel(this));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, fragment, UserLikesFragment.TAG);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_likes;
    }

}
