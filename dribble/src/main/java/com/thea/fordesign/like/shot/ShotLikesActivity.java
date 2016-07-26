package com.thea.fordesign.like.shot;

import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.ShotLikesActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class ShotLikesActivity extends BaseDataBindingActivity<ShotLikesActBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shot_likes;
    }
}
