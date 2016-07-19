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

        void followUser(@NonNull DribbbleUser requestedUser);

        void unFollowUser(@NonNull DribbbleUser requestedUser);
    }

    interface SubView extends BaseView<SubPresenter> {

        void showUser(@NonNull DribbbleUser user);

        void showShotsUi(@NonNull String shotsUrl);

        void showBucketsUi(@NonNull String bucketsUrl);

        void showProjectsUi(@NonNull String projectsUrl);

        void showFollowersUi(@NonNull String followersUrl);

        void showFollowingsUi(@NonNull String followingsUrl);

        void showLikesUi(@NonNull String likesUrl);

        void showWeb(@NonNull String web);

        void showTwitter(@NonNull String twitter);
    }

    interface SubPresenter extends BasePresenter {

        void openShots(@NonNull String shotsUrl);

        void openBuckets(@NonNull String bucketsUrl);

        void openProjects(@NonNull String projectsUrl);

        void openFollowers(@NonNull String followersUrl);

        void openFollowings(@NonNull String followingsUrl);

        void openLikes(@NonNull String likesUrl);

        void openWeb(String web);

        void openTwitter(String twitter);
    }
}
