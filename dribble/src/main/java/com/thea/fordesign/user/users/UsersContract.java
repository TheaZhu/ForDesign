package com.thea.fordesign.user.users;

import android.support.annotation.NonNull;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.user.UserItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface UsersContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showUsers(List<DribbbleUser> users);

        void insertUsers(List<DribbbleUser> users);

        void showUserDetailsUi(@NonNull DribbbleUser user, android.view.View v);
    }

    interface Presenter extends UserItemPresenter {

        void result(int requestCode, int resultCode);

        void loadUsers(String usersUrl);

        void loadMore(String shotsUrl, int page);

        void openUserDetails(@NonNull DribbbleUser requestedUser);

        void likeShot(@NonNull DribbbleShot shot, boolean like);

    }
}
