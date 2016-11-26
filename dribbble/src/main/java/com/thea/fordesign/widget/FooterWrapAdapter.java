package com.thea.fordesign.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.thea.fordesign.util.Preconditions;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class FooterWrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = FooterWrapAdapter.class.getSimpleName();

    private static final int TYPE_FOOTER = -4;

    private RecyclerView.Adapter mAdapter;
    private View mFooterView;

    public FooterWrapAdapter(@NonNull RecyclerView.Adapter adapter, @NonNull View footerView) {
        mAdapter = Preconditions.checkNotNull(adapter, "adapter cannot be null");
        mFooterView = Preconditions.checkNotNull(footerView, "footerView cannot be null");

        mAdapter.registerAdapterDataObserver(mDataObserver);
        setLoading(false);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER)
            return new ViewHolder(mFooterView);
        else
            return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < mAdapter.getItemCount())
            mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mAdapter.getItemCount())
            return TYPE_FOOTER;
        else
            return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    public void setLoading(boolean loading) {
        mFooterView.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver
            () {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            notifyItemRangeChanged(fromPosition, toPosition + itemCount);
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
