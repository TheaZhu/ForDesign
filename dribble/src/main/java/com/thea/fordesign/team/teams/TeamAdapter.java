package com.thea.fordesign.team.teams;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.bean.DribbbleTeam;
import com.thea.fordesign.databinding.TeamItemBinding;
import com.thea.fordesign.team.TeamItemPresenter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    protected WeakReference<Fragment> mFragmentWeakReference;
    private List<DribbbleTeam> mItems;

    private TeamItemPresenter mItemActionListener;

    public TeamAdapter(Fragment fragment, List<DribbbleTeam> teams, TeamItemPresenter
            itemListener) {
        mFragmentWeakReference = new WeakReference<>(fragment);
        mItems = teams;
        mItemActionListener = itemListener;
    }

    public void replaceData(List<DribbbleTeam> teams) {
        mItems = teams;
        notifyDataSetChanged();
    }

    public void insertData(List<DribbbleTeam> teams) {
        int start = mItems.size();
        mItems.addAll(teams);
        notifyItemRangeInserted(start, teams.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TeamItemBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent
                .getContext()), R.layout.card_team, parent, false);
        return new ViewHolder(viewDataBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeamItemBinding viewDataBinding = DataBindingUtil.getBinding(holder.itemView);
        DribbbleTeam item = mItems.get(position);
        viewDataBinding.setTeam(item);
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
