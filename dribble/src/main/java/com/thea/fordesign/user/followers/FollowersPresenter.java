package com.thea.fordesign.user.followers;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.util.Preconditions;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class FollowersPresenter implements FollowersContract.Presenter {
    public static final String TAG = FollowersPresenter.class.getSimpleName();
    private final FollowersContract.View mFollowersView;

    public FollowersPresenter(FollowersContract.View followersView) {
        mFollowersView = Preconditions.checkNotNull(followersView, "followersView cannot be null");

        mFollowersView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void loadFollowers(String url) {

    }

    @Override
    public void loadMore(String url, int page) {

    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser, View v) {
        mFollowersView.showUserDetailsUi(requestedUser, v);
    }
}
