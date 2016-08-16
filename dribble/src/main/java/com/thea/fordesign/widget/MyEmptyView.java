package com.thea.fordesign.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
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

    public MyEmptyView(Context context, ViewGroup parent) {
        mViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout
                .empty_layout, parent, false);
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

    public void setOnRetryClickListener(final View.OnClickListener clickListener) {
        mViewDataBinding.btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view);
            }
        });
    }

}
