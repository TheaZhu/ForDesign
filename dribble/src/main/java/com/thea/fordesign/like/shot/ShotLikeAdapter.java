package com.thea.fordesign.like.shot;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.bean.DribbbleShotLike;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.UserItemBinding;
import com.thea.fordesign.user.UserItemPresenter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotLikeAdapter extends RecyclerView.Adapter<ShotLikeAdapter.ViewHolder> {
    protected WeakReference<Fragment> mFragmentWeakReference;
    private List<DribbbleShotLike> mItems;

    private UserItemPresenter mItemActionListener;

    public ShotLikeAdapter(Fragment fragment, List<DribbbleShotLike> followers, UserItemPresenter
            itemListener) {
        mFragmentWeakReference = new WeakReference<>(fragment);
        mItems = followers;
        mItemActionListener = itemListener;
    }

    public void replaceData(List<DribbbleShotLike> likes) {
        mItems = likes;
        notifyDataSetChanged();
    }

    public void insertData(List<DribbbleShotLike> likes) {
        int start = mItems.size();
        mItems.addAll(likes);
        notifyItemRangeInserted(start, likes.size());
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
        DribbbleUser item = mItems.get(position).getUser();
        viewDataBinding.setUser(item);
        viewDataBinding.setActionHandler(mItemActionListener);

        Glide.with(mFragmentWeakReference.get())
                .load(item.getAvatarUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .placeholder(R.mipmap.default_user_avatar)
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
