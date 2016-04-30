package com.thea.fordesign.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.databinding.HomeFragmentBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseDataBindingFragment<HomeFragmentBinding> implements HomeContract.View {
    public static final String TAG = HomeFragment.class.getSimpleName();

    private HomeContract.Presenter mPresenter;

    public HomeFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    protected void setData() {
        mViewDataBinding.setActionHandler(mPresenter);
    }

    @Override
    public void setPresenter(@NonNull HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showSnack(String msg) {
    }

    @Override
    public void showSnack(@StringRes int resId) {

    }
}
