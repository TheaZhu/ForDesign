package com.thea.fordesign.like.shot;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleShotLike;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.user.UserItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotLikesContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean active, @StringRes int resId, boolean enableClick);

        void setLoadingError();

        void showLikes(List<DribbbleShotLike> likes);

        void insertLikes(List<DribbbleShotLike> likes);

        void showUserDetailsUi(@NonNull DribbbleUser user, android.view.View v);

    }

    interface Presenter extends UserItemPresenter {

        void result(int requestCode, int resultCode);

        void loadLikes();

        void loadMore(int page);

    }
}
