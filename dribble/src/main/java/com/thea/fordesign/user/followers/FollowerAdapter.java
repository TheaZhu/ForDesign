package com.thea.fordesign.user.followers;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.bean.DribbbleFollower;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.UserItemBinding;
import com.thea.fordesign.user.UserItemPresenter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ViewHolder> {
    protected WeakReference<Fragment> mFragmentWeakReference;
    private List<DribbbleFollower> mItems;

    private UserItemPresenter mItemActionListener;

    public FollowerAdapter(Fragment fragment, List<DribbbleFollower> followers, UserItemPresenter
            itemListener) {
        mFragmentWeakReference = new WeakReference<>(fragment);
        mItems = followers;
        mItemActionListener = itemListener;
    }

    public void replaceData(List<DribbbleFollower> followers) {
        mItems = followers;
        notifyDataSetChanged();
    }

    public void insertData(List<DribbbleFollower> followers) {
        int start = mItems.size();
        mItems.addAll(followers);
        notifyItemRangeInserted(start, followers.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent
                .getContext()), R.layout.card_user, parent, false);
        return new ViewHolder(viewDataBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
        DribbbleUser item = mItems.get(position).getFollower();
        viewDataBinding.setUser(item);
        viewDataBinding.setActionHandler(mItemActionListener);

        Glide.with(mFragmentWeakReference.get())
                .load(item.getAvatarUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .placeholder(R.mipmap.default_shot)
                .into(viewDataBinding.ivAvatar);
        viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
