package com.thea.fordesign.user.detail;

import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.wrapper.DribbbleCommWrapper;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UserDetailPresenter implements UserDetailContract.Presenter {
    private static final String TAG = UserDetailPresenter.class.getSimpleName();
    private final UserDetailContract.View mView;
    private final UserModel mUserModel;
    private DribbbleCommWrapper mWrapper;

    private DribbbleUser mUser;

    public UserDetailPresenter(UserDetailContract.View view, DribbbleUser user, UserModel
            userModel) {
        mView = view;
        mUserModel = userModel;
        mWrapper = DribbbleCommWrapper.getInstance();
        mUser = user;

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
    public void followUser() {
        mWrapper.followUser(mUserModel.getDribbbleUserAccessToken(), mUser.getId(), new
                DribbbleCommWrapper.FollowUserCallback() {

                    @Override
                    public void onSuccess() {
                        mView.showFollowed();
                    }

                    @Override
                    public void onFail(int errCode, String message) {
                        mView.showSnack(message + ": " + errCode);
                    }
                });
    }

    @Override
    public void unFollowUser() {
        mWrapper.unfollowUser(mUserModel.getDribbbleUserAccessToken(), mUser.getId(), new
                DribbbleCommWrapper.UnfollowUserCallback() {

                    @Override
                    public void onSuccess() {
                        mView.showUnfollowed();
                    }

                    @Override
                    public void onFail(int errCode, String message) {
                        mView.showSnack(message + ": " + errCode);
                    }
                });
    }

    @Override
    public void start() {
        LogUtil.i(TAG, "start");
        mWrapper.checkFollowingUser(mUserModel.getDribbbleUserAccessToken(), mUser.getId(), new
                DribbbleCommWrapper.CheckFollowingUserCallback() {
                    @Override
                    public void onSuccess(boolean following) {
                        if (following)
                            mView.showFollowed();
                        else
                            mView.showUnfollowed();
                    }

                    @Override
                    public void onFail(int errCode, String message) {
                    }
                });
    }
}
