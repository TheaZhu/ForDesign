package com.thea.fordesign.user.users;

import android.content.Intent;
import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.UsersActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class UsersActivity extends BaseDataBindingActivity<UsersActBinding> {
    public static final String EXTRA_TITLE = "activity_title";
    public static final String EXTRA_USERS_URL = "users_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra(EXTRA_TITLE));

        if (intent.hasExtra(EXTRA_USERS_URL)) {
            String mUserUrl = intent.getStringExtra(EXTRA_USERS_URL);
            UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fl_content);
            if (usersFragment == null) {
                usersFragment = UsersFragment.newInstance();
                ActivityUtil.addFragmentToActivity(
                        getSupportFragmentManager(), usersFragment, R.id.fl_content);
            }
            new UsersPresenter(usersFragment, mUserUrl, new UserModel(this));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_users;
    }
}
