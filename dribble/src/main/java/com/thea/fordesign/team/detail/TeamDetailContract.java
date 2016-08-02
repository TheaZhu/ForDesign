package com.thea.fordesign.team.detail;

import android.support.annotation.NonNull;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleUser;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface TeamDetailContract {

    interface View extends BaseView<Presenter> {

        void showTeam(DribbbleUser team);

        void showLikesUi(@NonNull String likesUrl);

        void showBucketsUi(@NonNull String bucketsUrl);

        void showProjectsUi(int teamId);

        void showMembersUi(@NonNull String membersUrl);

        void showFollowersUi(@NonNull String followersUrl);

        void showTeamShotsUi(@NonNull String teamShotsUrl);

        void showWeb(@NonNull String web);

        void showTwitter(@NonNull String twitter);

    }

    interface Presenter extends BasePresenter {

        void openShots(@NonNull String bucketsUrl);

        void openLikes(@NonNull String likesUrl);

        void openBuckets(@NonNull String bucketsUrl);

        void openProjects(int teamId);

        void openMembers(@NonNull String membersUrl);

        void openFollowers(@NonNull String followersUrl);

        void openFollowings(@NonNull String followingsUrl);

        void openTeamShots(@NonNull String teamShotsUrl);

        void openWeb(String web);

        void openTwitter(String twitter);
    }
}
