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
        mParentPresenter.openShots();
    }

    @Override
    public void openBuckets(@NonNull String bucketsUrl) {
        mDetailView.showBucketsUi(bucketsUrl);
    }

    @Override
    public void openTeams(@NonNull String teamsUrl) {
        mDetailView.showTeamsUi(teamsUrl);
    }

    @Override
    public void openFollowers(@NonNull String followersUrl) {
        mDetailView.showFollowersUi(followersUrl);
    }

    @Override
    public void openFollowings(@NonNull String followingsUrl) {
        mParentPresenter.openFollowings();
    }

    @Override
    public void openLikes(@NonNull String likesUrl) {
        mDetailView.showLikesUi(likesUrl);
    }

    @Override
    public void openProjects(int userId) {
        mDetailView.showProjectsUi(userId);
    }

    @Override
    public void openWeb(String web) {
        mDetailView.showWeb(web);
    }

    @Override
    public void openTwitter(String twitter) {
        mDetailView.showTwitter(twitter);
    }
}
