package com.thea.fordesign.like.data;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import com.thea.fordesign.DribbbleService;
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
public class UserLikesRepository implements UserLikesDataSource {
    public static final String TAG = UserLikesRepository.class.getSimpleName();

    private static volatile UserLikesRepository sInstance = null;

    private DribbbleService mService;

    Map<Integer, DribbbleUserLike> mCachedLikes;
    boolean mCacheIsDirty = false;

    private UserLikesRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static UserLikesRepository getInstance() {
        if (sInstance == null) {
            synchronized (UserLikesRepository.class) {
                if (sInstance == null)
                    sInstance = new UserLikesRepository();
            }
        }
        return sInstance;
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getLikes(@NonNull String authorization, final int page, int perPage, final LoadLikesCallback
            callback) {
        if (mCachedLikes == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get user likes");
            Call<List<DribbbleUserLike>> call = mService.getUserLikes(authorization, page);
            call.enqueue(new Callback<List<DribbbleUserLike>>() {
                @Override
                public void onResponse(Call<List<DribbbleUserLike>> call,
                                       Response<List<DribbbleUserLike>> response) {
                    LogUtil.i(TAG, "get user likes code: " + response.code() + ", message: " +
                            response.message());
                    onLikesLoaded(response.body(), page, callback);
                }

                @Override
                public void onFailure(Call<List<DribbbleUserLike>> call, Throwable t) {
                    LogUtil.i(TAG, "get user likes call executed: " + call.isExecuted() + ", url:" +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onLikesLoaded(new ArrayList<>(mCachedLikes.values()));
        }
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getLikes(@NonNull String authorization, @Nullable String url, final int page,
                         final LoadLikesCallback callback) {
        if (mCachedLikes == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get user likes");
            Call<List<DribbbleUserLike>> call = mService.getUserLikes(authorization, url, page);
            call.enqueue(new Callback<List<DribbbleUserLike>>() {
                @Override
                public void onResponse(Call<List<DribbbleUserLike>> call,
                                       Response<List<DribbbleUserLike>> response) {
                    LogUtil.i(TAG, "get user likes code: " + response.code() + ", message: " +
                            response.message());
                    onLikesLoaded(response.body(), page, callback);
                }

                @Override
                public void onFailure(Call<List<DribbbleUserLike>> call, Throwable t) {
                    LogUtil.i(TAG, "get user likes call executed: " + call.isExecuted() + ", url:" +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onLikesLoaded(new ArrayList<>(mCachedLikes.values()));
        }
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getLike(@NonNull String authorization, int likeId, GetLikeCallback callback) {
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void saveLike(@NonNull String authorization, @NonNull DribbbleUserLike like) {
    }

    @Override
    public void refreshLikes() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllLikes() {
        if (mCachedLikes == null) {
            mCachedLikes = new LinkedHashMap<>();
        }
        mCachedLikes.clear();
    }

    @Override
    public void deleteLike(int likeId) {
        mCachedLikes.remove(likeId);
    }

    private void onLikesLoaded(List<DribbbleUserLike> likes, int page, LoadLikesCallback callback) {
        refreshCache(page, likes);
        if (callback != null)
            callback.onLikesLoaded(likes);
    }

    private void refreshCache(int page, List<DribbbleUserLike> likes) {
        if (mCachedLikes == null) {
            mCachedLikes = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedLikes.clear();
        for (DribbbleUserLike like : likes) {
            mCachedLikes.put(like.getId(), like);
        }
        mCacheIsDirty = false;
    }
}
