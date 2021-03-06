package com.thea.fordesign.shot.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

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

        void showShotUnliked();

        void showShotToBucketUi(int shotId);

        void showUserDetailsUi(@NonNull DribbbleUser user, android.view.View v);

        void showLikesUi(@NonNull String likesUrl);

        void showCommentsUi(int shotId, @NonNull String commentsUrl);

        void showBucketsUi(@NonNull String bucketsUrl);

        void showMoreActionDialog(@NonNull DribbbleShot shot);

        void showInBrowser(@NonNull String url);

        void showShareChooser(@NonNull String url);

        void showSnack(@StringRes int resId, @StringRes int actionResId, android.view.View
                .OnClickListener clickListener);

        void hideMoreActionDialog();

    }

    interface Presenter extends UserItemPresenter {

        void result(int requestCode, int resultCode);

        void getShot();

        void likeShot(@NonNull DribbbleShot shot, boolean like);

        void bucketShot(@NonNull DribbbleShot shot);

        void shareShot(@NonNull DribbbleShot shot, int where);

        void openLikers(@NonNull DribbbleShot shot);

        void openComments(@NonNull DribbbleShot shot);

        void openBuckets(@NonNull DribbbleShot shot);

        void openInBrowser();

        void copyShotUrl(Context context);

        void shareShot();

        void saveImageToLocal(Context context);

        String formatTime(String timeStr);

        void moreActions();
    }
}
