package com.thea.fordesign.setting.feedback;

import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.FeedbackActBinding;

public class FeedbackActivity extends BaseDataBindingActivity<FeedbackActBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }
}
