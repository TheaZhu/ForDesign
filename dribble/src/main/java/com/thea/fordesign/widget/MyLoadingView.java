package com.thea.fordesign.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thea.fordesign.R;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class MyLoadingView {
    private View mView;
    private BallLoadingView mBallLoadingView;
    private TextView mMessage;

    private boolean mEnableClick = true;

    public MyLoadingView(Context context, ViewGroup parent) {
        mView = LayoutInflater.from(context).inflate(R.layout.loading_layout, parent, false);
        mBallLoadingView = (BallLoadingView) mView.findViewById(R.id.blv);
        mMessage = (TextView) mView.findViewById(R.id.tv_loading_message);
    }

    public View getView() {
        return mView;
    }

    public boolean isLoading() {
        return mBallLoadingView.isLoading();
    }

    public void setLoadingIndicator(boolean active, String message) {
        mBallLoadingView.setLoading(active);
        mMessage.setText(message);
    }

    public void enableClick(boolean enable) {
        mEnableClick = enable;
    }

    public void setOnClickListener(final View.OnClickListener clickListener, boolean enable) {
        mEnableClick = enable;
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEnableClick) {
                    clickListener.onClick(view);
                }
            }
        });
    }

}
