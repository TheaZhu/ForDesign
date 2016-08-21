package com.thea.fordesign.setting.licenses;

import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.LicensesActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class LicensesActivity extends BaseDataBindingActivity<LicensesActBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_licenses;
    }
}
