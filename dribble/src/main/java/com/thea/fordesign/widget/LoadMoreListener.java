package com.thea.fordesign.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {

    private RecyclerView.LayoutManager mLayoutManager;

    protected int visibleItemCount, totalItemCount, lastVisibleItem;
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
            lastVisibleItem = ((LinearLayoutManager) mLayoutManager)
                    .findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItem = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else {
            lastVisibleItem = totalItemCount;
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && totalItemCount - lastVisibleItem <= 2) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public void reset() {
        currentPage = 1;
        previousTotal = 0;
        loading = true;
    }

    public abstract void onLoadMore(int currentPage);
}
