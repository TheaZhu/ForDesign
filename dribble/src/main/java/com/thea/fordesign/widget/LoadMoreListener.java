package com.thea.fordesign.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {
    public static final String TAG = LoadMoreListener.class.getSimpleName();

    private RecyclerView.LayoutManager mLayoutManager;

    protected int visibleItemCount, totalItemCount, firstVisibleItem, lastVisibleItem;
    private boolean loading = true;
    private int previousTotal = 0;
    private int currentPage = 1;

    public LoadMoreListener(RecyclerView.LayoutManager LayoutManager) {
        mLayoutManager = LayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();
        if (mLayoutManager instanceof LinearLayoutManager) {
            firstVisibleItem = ((LinearLayoutManager) mLayoutManager)
                    .findFirstVisibleItemPosition();
            lastVisibleItem = ((LinearLayoutManager) mLayoutManager)
                    .findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            firstVisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            lastVisibleItem = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else {
            firstVisibleItem = 0;
            lastVisibleItem = totalItemCount;
        }

        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        boolean hasFooter = false;
        if (adapter instanceof FooterWrapAdapter) {
            totalItemCount--;
            hasFooter = true;
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                if (hasFooter)
                    ((FooterWrapAdapter) adapter).setLoading(loading);
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
            if (hasFooter && mLayoutManager.findViewByPosition(lastVisibleItem - 1).getBottom() >=
                    recyclerView.getBottom())
                ((FooterWrapAdapter) adapter).setLoading(loading);
        }
    }

    public void reset() {
        currentPage = 1;
        previousTotal = 0;
        loading = true;
    }

    public abstract void onLoadMore(int currentPage);
}
