package com.thea.fordesign.user.users;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.user.UserItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface UsersContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean visible, boolean active, @StringRes int resId, boolean
                enableClick);

        void setLoadingError();

        void showUsers(List<DribbbleUser> users);

        void insertUsers(List<DribbbleUser> users);

        void showUserDetailsUi(@NonNull DribbbleUser user, android.view.View v);

    }

    interface Presenter extends UserItemPresenter {

        void loadUsers();

        void loadMore(int page);

    }
}
