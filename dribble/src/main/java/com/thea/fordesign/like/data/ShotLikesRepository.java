package com.thea.fordesign.like.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.bean.DribbbleShotLike;
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
public class ShotLikesRepository implements ShotLikesDataSource {
    public static final String TAG = ShotLikesRepository.class.getSimpleName();

    private DribbbleService mService;
    Map<Integer, DribbbleShotLike> mCachedShotLikes;
    boolean mCacheIsDirty = false;

    private ShotLikesRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static ShotLikesRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void getShotLikes(@NonNull String authorization, @Nullable String url, final int page,
                             final LoadLikesCallback callback) {
        if (mCachedShotLikes == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get shot likes");
            Call<List<DribbbleShotLike>> call = mService.getShotLikes(authorization, url, page);
            call.enqueue(new Callback<List<DribbbleShotLike>>() {
                @Override
                public void onResponse(Call<List<DribbbleShotLike>> call,
                                       Response<List<DribbbleShotLike>> response) {
                    LogUtil.i(TAG, "get shot likes code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleShotLike> likes = response.body();
                    refreshCache(page, likes);
                    if (callback != null)
                        callback.onShotLikesLoaded(likes);
                }

                @Override
                public void onFailure(Call<List<DribbbleShotLike>> call, Throwable t) {
                    LogUtil.i(TAG, "get shot likes call executed: " + call.isExecuted() + ", url:" +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onShotLikesLoaded(new ArrayList<>(mCachedShotLikes.values()));
        }
    }

    public void refreshLikes() {
        mCacheIsDirty = true;
    }

    private void refreshCache(int page, List<DribbbleShotLike> followings) {
        if (mCachedShotLikes == null) {
            mCachedShotLikes = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedShotLikes.clear();
        for (DribbbleShotLike like : followings) {
            mCachedShotLikes.put(like.getId(), like);
        }
        mCacheIsDirty = false;
    }

    private static class Singleton {
        private static final ShotLikesRepository INSTANCE = new ShotLikesRepository();
    }
}
