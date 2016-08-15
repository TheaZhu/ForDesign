package com.thea.fordesign.base;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    protected boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (mRootView == null) mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterCreate(savedInstanceState);
    }

    public void showSnack(final String msg) {
        mRootView.post(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(mRootView, msg, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void showSnack(@StringRes int resId) {
        showSnack(getString(resId));
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    protected void onInvisible() {
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();
}
