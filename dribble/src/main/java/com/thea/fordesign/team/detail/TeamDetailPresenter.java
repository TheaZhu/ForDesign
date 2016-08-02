package com.thea.fordesign.team.detail;

import android.support.annotation.NonNull;

import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.user.detail.UserDetailContract;
import com.thea.fordesign.util.Preconditions;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class TeamDetailPresenter implements TeamDetailContract.Presenter {
    private final TeamDetailContract.View mDetailView;
    private UserDetailContract.Presenter mParentPresenter;
    private DribbbleUser mTeam;

    public TeamDetailPresenter(@NonNull TeamDetailContract.View detailView, UserDetailContract
            .Presenter presenter, DribbbleUser team) {
        mDetailView = Preconditions.checkNotNull(detailView, "detailView cannot be null");
        mParentPresenter = presenter;
        mTeam = team;

        mDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        mDetailView.showTeam(mTeam);
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
    public void openFollowers(@NonNull String followersUrl) {
        mDetailView.showFollowersUi(followersUrl);
    }

    @Override
    public void openFollowings(@NonNull String followingsUrl) {
        mParentPresenter.openFollowings();
    }

    @Override
    public void openTeamShots(@NonNull String teamShotsUrl) {
        mDetailView.showTeamShotsUi(teamShotsUrl);
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
    public void openMembers(@NonNull String membersUrl) {
        mDetailView.showMembersUi(membersUrl);
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
