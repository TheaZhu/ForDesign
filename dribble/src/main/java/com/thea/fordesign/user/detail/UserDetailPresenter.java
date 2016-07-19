package com.thea.fordesign.user.detail;

import android.support.annotation.NonNull;

import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.util.LogUtil;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UserDetailPresenter implements UserDetailContract.Presenter {
    private static final String TAG = UserDetailPresenter.class.getSimpleName();
    private final UserDetailContract.View mView;

    public UserDetailPresenter(UserDetailContract.View view) {
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void openUserDetails() {
        mView.showUserDetailsUi();
    }

    @Override
    public void openShots() {
        mView.showShotsUi();
    }

    @Override
    public void openFollowings() {
        mView.showFollowingsUi();
    }

    @Override
    public void openLikes() {
        mView.showLikesUi();
    }

    @Override
    public void followUser(@NonNull DribbbleUser requestedUser) {
        LogUtil.i(TAG, "登录功能开发中");
        mView.showFollowed();
    }

    @Override
    public void unFollowUser(@NonNull DribbbleUser requestedUser) {
        LogUtil.i(TAG, "登录功能开发中");
        mView.showUnfollowed();
    }

    @Override
    public void start() {
    }
}
