package com.thea.fordesign.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public abstract class BaseDataBindingActivity<T extends ViewDataBinding> extends BaseActivity {
    protected T mViewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
        setContentView(mViewDataBinding.getRoot());
    }

    protected abstract int getLayoutId();

    public void showSnack(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(mViewDataBinding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void showSnack(@StringRes int resId) {
        showSnack(getString(resId));
    }

}
