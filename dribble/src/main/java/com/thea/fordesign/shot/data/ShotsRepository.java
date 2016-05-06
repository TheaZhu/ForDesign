package com.thea.fordesign.shot.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.DribbleConstant;
import com.thea.fordesign.bean.DribbbleShot;
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

    private static ShotsRepository sInstance = null;

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
    public void getShots(@Nullable String list, @Nullable String sort, @Nullable String timeframe,
                         @Nullable String date, final LoadShotsCallback callback) {
        if (mCachedShots == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get shots");
            if (list == null)
                list = DribbleConstant.SHOT_LIST_DEFAULT;
            if (timeframe == null)
                timeframe = DribbleConstant.SHOT_TIME_FRAME_DEFAULT;
            if (sort == null)
                sort = DribbleConstant.SHOT_SORT_DEFAULT;
            Call<List<DribbbleShot>> call = mService.getShots(DribbleConstant.AUTH_TYPE +
                    DribbleConstant.CLIENT_ACCESS_TOKEN, list, sort, timeframe, null, 0, 12);
            call.enqueue(new Callback<List<DribbbleShot>>() {
                @Override
                public void onResponse(Call<List<DribbbleShot>> call,
                                       Response<List<DribbbleShot>> response) {
                    LogUtil.i(TAG, "get shots code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleShot> shots = response.body();
                    refreshCache(shots);
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
    public void getShot(int shotId, final GetShotCallback callback) {
        DribbbleShot cachedShot = getShotWithId(shotId);

        // Respond immediately with cache if available
        if (cachedShot != null) {
            if (callback != null)
                callback.onShotLoaded(cachedShot);
        } else {
            LogUtil.i(TAG, "get shot: " + shotId);
            Call<DribbbleShot> call = mService.getShot(DribbleConstant.AUTH_TYPE +
                    DribbleConstant.CLIENT_ACCESS_TOKEN, shotId);
            call.enqueue(new Callback<DribbbleShot>() {
                @Override
                public void onResponse(Call<DribbbleShot> call, Response<DribbbleShot> response) {
                    LogUtil.i(TAG, "get shot code: " + response.code() + ", message: " + response
                            .message());
                    if (callback != null)
                        callback.onShotLoaded(response.body());
                }

                @Override
                public void onFailure(Call<DribbbleShot> call, Throwable t) {
                    LogUtil.i(TAG, "get shot call executed: " + call.isExecuted() + ", url: " + call
                            .request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        }

    }

    @Override
    public void saveShot(@NonNull DribbbleShot Shot) {

    }

    @Override
    public void likeShot(@NonNull DribbbleShot shot) {

    }

    @Override
    public void likeShot(int shotId) {

    }

    @Override
    public void unlikeShot(@NonNull DribbbleShot shot) {

    }

    @Override
    public void unlikeShot(int shotId) {

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

    private void refreshCache(List<DribbbleShot> shots) {
        if (mCachedShots == null) {
            mCachedShots = new LinkedHashMap<>();
        }
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
