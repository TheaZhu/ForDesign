package com.thea.fordesign.shot.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.DribbbleConstant;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUserLike;
import com.thea.fordesign.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotsRepository implements ShotsDataSource {
    public static final String TAG = ShotsRepository.class.getSimpleName();

    private static volatile ShotsRepository sInstance = null;

    private DribbbleService mService;

    //    List<DribbbleShot> mCachedShots;
    Map<Integer, DribbbleShot> mCachedShots;
    boolean mCacheIsDirty = false;

    private ShotsRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static ShotsRepository getInstance() {
        if (sInstance == null) {
            synchronized (ShotsRepository.class) {
                if (sInstance == null)
                    sInstance = new ShotsRepository();
            }
        }
        return sInstance;
    }

    @Override
    public void getShots(@NonNull String authorization, @Nullable String list, @Nullable String
            sort, @Nullable String timeframe, @Nullable String date, final int page, int perPage,
                         final LoadShotsCallback callback) {
        if (mCachedShots == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get shots");
            if (list == null)
                list = DribbbleConstant.SHOT_LIST_DEFAULT;
            if (timeframe == null)
                timeframe = DribbbleConstant.SHOT_TIME_FRAME_DEFAULT;
            if (sort == null)
                sort = DribbbleConstant.SHOT_SORT_DEFAULT;
            Call<List<DribbbleShot>> call = mService.getShots(authorization, list, sort,
                    timeframe, null, page, perPage);
            call.enqueue(new Callback<List<DribbbleShot>>() {
                 @Override
                 public void onResponse(Call<List<DribbbleShot>> call,
                                        Response<List<DribbbleShot>> response) {
                     LogUtil.i(TAG, "get shots code: " + response.code() + ", message: " +
                             response.message());
                     if (response.code() == 200) {
                         List<DribbbleShot> shots = response.body();
                         refreshCache(page, shots);
                         if (callback != null) callback.onShotsLoaded(shots);
                     } else if (callback != null) {
                         callback.onDataNotAvailable();
                     }
                 }

                 @Override
                 public void onFailure(Call<List<DribbbleShot>> call, Throwable t) {
                     LogUtil.i(TAG, "get shots call executed: " + call.isExecuted() +
                             ", url: " + call.request().url());
                     t.printStackTrace();
                     if (callback != null)
                         callback.onDataNotAvailable();
                 }
             }

            );
        } else if (callback != null) {
            callback.onShotsLoaded(new ArrayList<>(mCachedShots.values()));
        }
    }

    @Override
    public void getShots(@NonNull String authorization, @Nullable String url, final int page,
                         final LoadShotsCallback callback) {
        if (mCachedShots == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get shots");
            Call<List<DribbbleShot>> call = mService.getShots(authorization, url, page);
            call.enqueue(new Callback<List<DribbbleShot>>() {
                @Override
                public void onResponse(Call<List<DribbbleShot>> call,
                                       Response<List<DribbbleShot>> response) {
                    LogUtil.i(TAG, "get shots code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleShot> shots = response.body();
                    refreshCache(page, shots);
                    if (callback != null)
                        callback.onShotsLoaded(shots);
                }

                @Override
                public void onFailure(Call<List<DribbbleShot>> call, Throwable t) {
                    LogUtil.i(TAG, "get shots call executed: " + call.isExecuted() + ", url: " +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onShotsLoaded(new ArrayList<>(mCachedShots.values()));
        }
    }

    @Override
    public void getShot(@NonNull String authorization, int shotId,
                        final GetShotCallback callback) {
        DribbbleShot cachedShot = getShotWithId(shotId);

        // Respond immediately with cache if available
        if (cachedShot != null) {
            LogUtil.i(TAG, "get shot local: " + shotId);
            if (callback != null)
                callback.onShotLoaded(cachedShot);
        } else {
            LogUtil.i(TAG, "get shot: " + shotId);
            Call<DribbbleShot> call = mService.getShot(authorization, shotId);
            call.enqueue(new Callback<DribbbleShot>() {
                @Override
                public void onResponse(Call<DribbbleShot> call, Response<DribbbleShot>
                        response) {
                    LogUtil.i(TAG, "get shot code: " + response.code() + ", message: " +
                            response
                                    .message());
                    if (callback != null)
                        callback.onShotLoaded(response.body());
                }

                @Override
                public void onFailure(Call<DribbbleShot> call, Throwable t) {
                    LogUtil.i(TAG, "get shot call executed: " + call.isExecuted() + ", url: "
                            + call
                            .request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        }

    }

    @Override
    public void saveShot(@NonNull String authorization, @NonNull DribbbleShot shot) {

    }

    @Override
    public void likeShot(@NonNull String authorization, @NonNull DribbbleShot shot,
                         LikeShotCallback callback) {
        likeShot(authorization, shot.getId(), callback);
    }

    @Override
    public void likeShot(@NonNull String authorization, int shotId, final LikeShotCallback
            callback) {
        Call<DribbbleUserLike> call = mService.likeShot(authorization, shotId);
        call.enqueue(new Callback<DribbbleUserLike>() {
            @Override
            public void onResponse(Call<DribbbleUserLike> call, Response<DribbbleUserLike>
                    response) {
                LogUtil.i(TAG, "like shot code: " + response.code() + ", message: " + response
                        .message());
                if (callback != null) {
                    int code = response.code();
                    if (code == 201)
                        callback.onSuccess(response.body());
                    else
                        callback.onFail(code, response.message());
                }
            }

            @Override
            public void onFailure(Call<DribbbleUserLike> call, Throwable t) {
                LogUtil.i(TAG, "get shot call executed: " + call.isExecuted() + ", url: " + call
                        .request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFail(DribbbleConstant.CODE_ERROR, t.getMessage());

            }
        });
    }

    @Override
    public void dislikeShot(@NonNull String authorization, @NonNull DribbbleShot shot) {

    }

    @Override
    public void dislikeShot(@NonNull String authorization, int shotId) {

    }

    @Override
    public void refreshShots() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllShots() {
        if (mCachedShots == null) {
            mCachedShots = new LinkedHashMap<>();
        }
        mCachedShots.clear();
    }

    @Override
    public void deleteShot(int shotId) {
        mCachedShots.remove(shotId);
    }

    private void refreshCache(int page, List<DribbbleShot> shots) {
        if (mCachedShots == null) {
            mCachedShots = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedShots.clear();
        for (DribbbleShot shot : shots) {
            mCachedShots.put(shot.getId(), shot);
        }
        mCacheIsDirty = false;
    }

    @Nullable
    private DribbbleShot getShotWithId(int id) {
        if (mCachedShots == null || mCachedShots.isEmpty()) {
            return null;
        } else {
            return mCachedShots.get(id);
        }
    }
}
