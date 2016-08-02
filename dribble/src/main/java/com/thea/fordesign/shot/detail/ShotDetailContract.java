package com.thea.fordesign.shot.detail;

import android.support.annotation.NonNull;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.user.UserItemPresenter;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotDetailContract {

    interface View extends BaseView<Presenter> {

        void showShot(DribbbleShot shot);

        void showShotLiked();

        void showShotDisliked();

        void showUserDetailsUi(@NonNull DribbbleUser user, android.view.View v);

        void showLikesUi(@NonNull String likesUrl);

        void showCommentsUi(int shotId, @NonNull String commentsUrl);

        void showBucketsUi(@NonNull String bucketsUrl);

    }

    interface Presenter extends UserItemPresenter {

        void result(int requestCode, int resultCode);

        void getShot();

        void likeShot(@NonNull DribbbleShot shot);

        void shareShot(@NonNull DribbbleShot shot, int where);

        void openLikers(@NonNull DribbbleShot shot);

        void openComments(@NonNull DribbbleShot shot);

        void openBuckets(@NonNull DribbbleShot shot);

        String formatTime(String timeStr);
    }
}
