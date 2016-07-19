package com.thea.fordesign.shot.shots;

import android.databinding.BaseObservable;
import android.view.View;

import com.thea.fordesign.bean.DribbbleShot;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotItemActionHandler extends BaseObservable {
    public static final String TAG = ShotItemActionHandler.class.getSimpleName();

    private ShotsContract.Presenter mListener;
    private boolean isLiked = false;

    public ShotItemActionHandler(ShotsContract.Presenter listener) {
        mListener = listener;
    }

    public String formatTime(String timeStr) {
        /*SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ", Locale
                .getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return dateFormat.format(parseFormat.parse(timeStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return timeStr.substring(0, timeStr.indexOf("T"));
    }

    public void likeChanged(DribbbleShot shot) {
        isLiked = !isLiked;
        mListener.likeShot(shot, isLiked);
    }

    public void shotClicked(DribbbleShot shot, View v) {
        mListener.openShotDetails(shot, v);
    }
}
