package com.thea.fordesign.shot.shots;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class SwipeRefreshLayoutDataBinding {

//    @BindingAdapter("onRefresh")
    public static void setSwipeRefreshLayoutOnRefreshListener(SwipeRefreshLayout view, final
    ShotsContract.Presenter presenter) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadShots(true);
            }
        });
    }
}
