package com.thea.fordesign.shot.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.bean.DribbbleShot;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotsDataSource {

    interface LoadShotsCallback {

        void onShotsLoaded(List<DribbbleShot> shots);

        void onDataNotAvailable();
    }

    interface GetShotCallback {

        void onShotLoaded(DribbbleShot shot);

        void onDataNotAvailable();
    }

    void getShots(@Nullable String list, @Nullable String timeframe, @Nullable String date,
                  @Nullable String sort, LoadShotsCallback callback);

    void getShot(int shotId, GetShotCallback callback);

    void saveShot(@NonNull DribbbleShot Shot);

    void likeShot(@NonNull DribbbleShot shot);

    void likeShot(int shotId);

    void unlikeShot(@NonNull DribbbleShot shot);

    void unlikeShot(int shotId);

    void refreshShots();

    void deleteAllShots();

    void deleteShot(int shotId);

}
