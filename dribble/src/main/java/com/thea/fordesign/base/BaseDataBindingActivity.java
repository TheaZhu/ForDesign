package com.thea.fordesign.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

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

}
