package com.thea.fordesign.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thea.fordesign.R;
import com.thea.fordesign.databinding.EmptyLayoutBinding;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class MyEmptyView {
    private EmptyLayoutBinding mViewDataBinding;

    private ViewGroup mParent;
    private boolean isShowing = false;

    public MyEmptyView(Context context, ViewGroup parent) {
        mParent = parent;
        mViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                .layout_empty, parent, false);
    }

    public View getView() {
        return mViewDataBinding.getRoot();
    }

    public void setEmptyMessage(String message) {
        mViewDataBinding.tvEmptyMessage.setText(message);
    }

    public void enableRetryBtn(boolean enable) {
        mViewDataBinding.btnRetry.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void setOnRetryClickListener(@StringRes int stringRes, final View.OnClickListener clickListener) {
        mViewDataBinding.btnRetry.setText(stringRes);
        mViewDataBinding.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view);
            }
        });
    }

    public void show(String message) {
        if (!isShowing) {
            if (getView().getParent() == null) {
                mParent.addView(getView());
            }
            isShowing = true;
        }
        setEmptyMessage(message);
    }

    public void dismiss() {
        if (isShowing) {
            if (getView().getParent() == mParent)
                mParent.removeView(getView());
            isShowing = false;
        }
    }

}
