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
                  @Nullable String sort, int page, int perPage, LoadShotsCallback callback);

    void getShots(@Nullable String url, int page, LoadShotsCallback callback);

    void getShot(int shotId, GetShotCallback callback);

    void saveShot(@NonNull DribbbleShot shot);

    void likeShot(@NonNull DribbbleShot shot);

    void likeShot(int shotId);

    void dislikeShot(@NonNull DribbbleShot shot);

    void dislikeShot(int shotId);

    void refreshShots();

    void deleteAllShots();

    void deleteShot(int shotId);

}
