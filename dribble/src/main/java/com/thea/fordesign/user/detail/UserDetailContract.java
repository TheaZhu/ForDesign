package com.thea.fordesign.user.detail;

import android.support.annotation.NonNull;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleUser;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface UserDetailContract {

    interface View extends BaseView<Presenter> {

        void showUser(DribbbleUser user);

        void showFollowed();

        void showUnfollowed();

        void showUserDetailsUi();

        void showShotsUi();

        void showFollowingsUi();

        void showLikesUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void openUserDetails();

        void openShots();

        void openFollowings();

        void openLikes();

        void followUser();

        void unFollowUser();
    }

    interface SubView extends BaseView<SubPresenter> {

        void showLikesUi(@NonNull String likesUrl);

        void showBucketsUi(@NonNull String bucketsUrl);

        void showProjectsUi(int userId);

        void showFollowersUi(@NonNull String followersUrl);

        void showTeamsUi(@NonNull String teamsUrl);

        void showWeb(@NonNull String web);

        void showTwitter(@NonNull String twitter);
    }

    interface SubPresenter extends BasePresenter {

        void openShots(@NonNull String bucketsUrl);

        void openBuckets(@NonNull String bucketsUrl);

        void openLikes(@NonNull String likesUrl);

        void openProjects(int userId);

        void openFollowers(@NonNull String followersUrl);

        void openFollowings(@NonNull String followingsUrl);

        void openTeams(@NonNull String teamsUrl);

        void openWeb(String web);

        void openTwitter(String twitter);
    }
}
