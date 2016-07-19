package com.thea.fordesign.user;

import android.support.annotation.NonNull;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.bean.DribbbleUser;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface UserItemPresenter extends BasePresenter {

    void openUserDetails(@NonNull DribbbleUser requestedUser, android.view.View v);
}
