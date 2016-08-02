package com.thea.fordesign.user.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    void getUsers(@NonNull String authorization, @Nullable String url, int page,
                  LoadUsersCallback callback);

    void getUser(@NonNull String authorization, int userId, GetUserCallback callback);

    void saveUser(@NonNull String authorization, @NonNull DribbbleUser user);

    void refreshUsers();

    void deleteAllUsers();

    void deleteUser(int userId);

}
