package com.thea.fordesign.shot;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.bean.DribbbleShot;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotItemPresenter extends BasePresenter {

    String formatTime(String timeStr);

    /*public void likeChanged(DribbbleShot shot) {
        isLiked = !isLiked;
        mListener.likeShot(shot, isLiked);
    }*/

    void openShotDetails(@NonNull DribbbleShot shot, View v);
}
