package com.thea.fordesign.user.data;

import android.support.annotation.NonNull;

import com.thea.fordesign.bean.DribbbleUser;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface UsersDataSource {

    interface LoadUsersCallback {

        void onUsersLoaded(List<DribbbleUser> users);

        void onDataNotAvailable();
    }

    interface GetUserCallback {

        void onUserLoaded(DribbbleUser user);

        void onDataNotAvailable();
    }

    void getUser(int userId, GetUserCallback callback);

    void saveUser(@NonNull DribbbleUser user);

    void deleteUser(int userId);

}
