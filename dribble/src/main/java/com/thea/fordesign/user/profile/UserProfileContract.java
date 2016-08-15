package com.thea.fordesign.user.profile;

import android.support.annotation.NonNull;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface UserProfileContract {

    interface View extends BaseView<Presenter> {

        void showShotsUi(@NonNull String shotsUrl);

        void showFollowingsUi(@NonNull String followingUrl);

        void showLikesUi(@NonNull String likesUrl);

        void showBucketsUi(@NonNull String bucketsUrl);

        void showProjectsUi(int userId);

        void showFollowersUi(@NonNull String followersUrl);

        void showPrevious();

        void showSettings();

    }

    interface Presenter extends BasePresenter {

        void openShots(@NonNull String shotsUrl);

        void openBuckets(@NonNull String bucketsUrl);

        void openLikes(@NonNull String likesUrl);

        void openProjects(int userId);

        void openFollowers(@NonNull String followersUrl);

        void openFollowings(@NonNull String followingsUrl);

        void close();

        void settings();

    }

}
