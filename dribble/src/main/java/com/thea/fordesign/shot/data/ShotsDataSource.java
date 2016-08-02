package com.thea.fordesign.shot.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUserLike;

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

    interface LikeShotCallback {

        void onSuccess(DribbbleUserLike like);

        void onFail(int errCode, String message);
    }

    interface UnlikeShotCallback {

        void onSuccess();

        void onFail(int errCode, String message);
    }

    interface CheckLikeShotCallback{

        void onSuccess(boolean like);

        void onFail(int errCode, String message);
    }

    void getShots(@NonNull String authorization, @Nullable String list, @Nullable String timeframe,
                  @Nullable String date, @Nullable String sort, int page, int perPage,
                  LoadShotsCallback callback);

    void getShots(@NonNull String authorization, @Nullable String url, int page,
                  LoadShotsCallback callback);

    void getShot(@NonNull String authorization, int shotId, GetShotCallback callback);

    void saveShot(@NonNull String authorization, @NonNull DribbbleShot shot);

    void likeShot(@NonNull String authorization, @NonNull DribbbleShot shot, LikeShotCallback callback);

    void likeShot(@NonNull String authorization, int shotId, LikeShotCallback callback);

    void unlikeShot(@NonNull String authorization, int shotId, UnlikeShotCallback callback);

    void checkLikeShot(@NonNull String authorization, int shotId, CheckLikeShotCallback callback);

    void refreshShots();

    void deleteAllShots();

    void deleteShot(int shotId);

}
