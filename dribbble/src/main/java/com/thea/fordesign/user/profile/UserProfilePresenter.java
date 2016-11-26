package com.thea.fordesign.user.profile;

import android.support.annotation.NonNull;

import com.thea.fordesign.util.Preconditions;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UserProfilePresenter implements UserProfileContract.Presenter {
    private final UserProfileContract.View mView;

    public UserProfilePresenter(UserProfileContract.View view) {
        mView = Preconditions.checkNotNull(view, "view cannot be null");

        mView.setPresenter(this);
    }


    @Override
    public void openShots(@NonNull String shotsUrl) {
        mView.showShotsUi(shotsUrl);
    }

    @Override
    public void openBuckets(@NonNull String bucketsUrl) {
        mView.showBucketsUi(bucketsUrl);
    }

    @Override
    public void openLikes(@NonNull String likesUrl) {
        mView.showLikesUi(likesUrl);
    }

    @Override
    public void openProjects(int userId) {
        mView.showProjectsUi(userId);
    }

    @Override
    public void openFollowers(@NonNull String followersUrl) {
        mView.showFollowersUi(followersUrl);
    }

    @Override
    public void openFollowings(@NonNull String followingsUrl) {
        mView.showFollowingsUi(followingsUrl);
    }

    @Override
    public void close() {
        mView.showPrevious();
    }

    @Override
    public void settings() {
        mView.showSettings();
    }

    @Override
    public void start() {

    }
}
