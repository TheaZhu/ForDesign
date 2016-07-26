package com.thea.fordesign.like.user;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleUserLike;
import com.thea.fordesign.shot.ShotItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface UserLikesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showLikes(List<DribbbleUserLike> likes);

        void insertLikes(List<DribbbleUserLike> likes);

        void showShotDetailsUi(int shotId, String imageUrl, android.view.View v);

//        void showUserDetailsUi(int userId);

//        void setShotLikeState(boolean like);

    }

    interface Presenter extends ShotItemPresenter {

        void result(int requestCode, int resultCode);

        void loadLikes(String likesUrl);

        void loadMore(String likesUrl, int page);

//        void likeShot(@NonNull DribbbleShot shot, boolean like);

    }
}
