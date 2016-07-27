package com.thea.fordesign.user.followers;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleFollower;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.user.UserItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface FollowersContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean active, @StringRes int resId, boolean enableClick);

        void setLoadingError();

        void showFollowers(List<DribbbleFollower> followers);

        void insertFollowers(List<DribbbleFollower> followers);

        void showUserDetailsUi(@NonNull DribbbleUser user, android.view.View v);
    }

    interface Presenter extends UserItemPresenter {

        void result(int requestCode, int resultCode);

        void loadFollowers(String url);

        void loadMore(String url, int page);
    }
}
