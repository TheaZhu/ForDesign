package com.thea.fordesign.shot.detail;

import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseActivity;

public class ShotDetailActivity extends BaseActivity {

    public static final String EXTRA_SHOT_ID = "shot_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_detail);
    }
}
