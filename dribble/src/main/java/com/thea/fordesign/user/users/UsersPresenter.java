package com.thea.fordesign.user.users;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.util.Preconditions;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UsersPresenter implements UsersContract.Presenter {
    public static final String TAG = UsersPresenter.class.getSimpleName();
    private final UsersContract.View mUsersView;

    public UsersPresenter(UsersContract.View usersView) {
        mUsersView = Preconditions.checkNotNull(usersView, "usersView cannot be null");

        mUsersView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void loadUsers(String usersUrl) {

    }

    @Override
    public void loadMore(String shotsUrl, int page) {

    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser) {

    }

    @Override
    public void likeShot(@NonNull DribbbleShot shot, boolean like) {

    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser, View v) {

    }

    @Override
    public void start() {

    }
}
