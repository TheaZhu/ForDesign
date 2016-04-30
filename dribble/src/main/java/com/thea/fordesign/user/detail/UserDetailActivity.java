package com.thea.fordesign.user.detail;

import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseActivity;

public class UserDetailActivity extends BaseActivity {

    public static final String EXTRA_USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
    }
}
