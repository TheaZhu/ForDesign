package com.thea.fordesign.shots;

import android.databinding.BaseObservable;

import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
        SimpleDateFormat parseFormat = new SimpleDateFormat("YYYY-MM-DDTHH:MM:SSZ", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD", Locale.getDefault());
        try {
            return dateFormat.format(parseFormat.parse(timeStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    public void likeChanged(DribbbleShot shot) {
        isLiked = !isLiked;
        mListener.likeShot(shot, isLiked);
    }

    public void shotClicked(DribbbleShot shot) {
        mListener.openShotDetails(shot);
    }

    public void designerClicked(DribbbleUser user) {
        mListener.openUserDetails(user);
    }


}
