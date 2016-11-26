package com.thea.fordesign.setting.about;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.AboutActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class AboutActivity extends BaseDataBindingActivity<AboutActBinding> implements
        AboutContract.View {

    private AboutContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter = new AboutPresenter(this);

        mViewDataBinding.setActionHandler(mPresenter);
        mViewDataBinding.tvVersion.setText(getString(R.string.version) + " " + getCurrentVersion());
    }

    private String getCurrentVersion() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return getString(R.string.version_not_found);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void setPresenter(@NonNull AboutContract.Presenter presenter) {
    }
}
