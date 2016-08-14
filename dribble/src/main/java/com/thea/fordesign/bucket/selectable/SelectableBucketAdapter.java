package com.thea.fordesign.bucket.selectable;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thea.fordesign.R;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.databinding.SelectableBucketItemBinding;
import com.thea.fordesign.widget.SimpleViewHolder;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class SelectableBucketAdapter extends RecyclerView.Adapter<SimpleViewHolder>  {

    private List<DribbbleBucket> mBuckets;

    private ShotToBucketsContract.Presenter mUserActionsListener;

    public SelectableBucketAdapter(List<DribbbleBucket> buckets, ShotToBucketsContract.Presenter itemListener) {
        mBuckets = buckets;
        mUserActionsListener = itemListener;
    }

    public void replaceData(List<DribbbleBucket> buckets) {
        mBuckets = buckets;
        notifyDataSetChanged();
    }

    public void insertData(List<DribbbleBucket> buckets) {
        int start = mBuckets.size();
        mBuckets.addAll(buckets);
        notifyItemRangeInserted(start, buckets.size());
    }

    public void insertItem(DribbbleBucket bucket) {
        mBuckets.add(0, bucket);
        notifyItemInserted(0);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectableBucketItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from
                (parent.getContext()), R.layout.card_selectable_bucket, parent, false);
        return new SimpleViewHolder(viewDataBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        SelectableBucketItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
        DribbbleBucket bucket = mBuckets.get(position);
        viewDataBinding.setBucket(bucket);
        viewDataBinding.setActionHandler(mUserActionsListener);
        viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mBuckets.size();
    }
}
