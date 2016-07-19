package com.thea.fordesign.user.detail;

import android.support.annotation.NonNull;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UserDetailFragPresenter implements UserDetailContract.SubPresenter {
    private final UserDetailContract.SubView mDetailView;
    private UserDetailContract.Presenter mParentPresenter;

    public UserDetailFragPresenter(UserDetailContract.SubView detailView, UserDetailContract
            .Presenter presenter) {
        mDetailView = detailView;
        mParentPresenter = presenter;

        mDetailView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void openShots(@NonNull String shotsUrl) {

    }

    @Override
    public void openBuckets(@NonNull String bucketsUrl) {

    }

    @Override
    public void openProjects(@NonNull String projectsUrl) {

    }

    @Override
    public void openFollowers(@NonNull String followersUrl) {

    }

    @Override
    public void openFollowings(@NonNull String followingsUrl) {

    }

    @Override
    public void openLikes(@NonNull String likesUrl) {

    }

    @Override
    public void openWeb(String web) {

    }

    @Override
    public void openTwitter(String twitter) {

    }
}
