package com.thea.fordesign.user.data;

import android.support.annotation.Nullable;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.DribbleConstant;
import com.thea.fordesign.bean.DribbbleFollower;
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
public class FollowingsRepository implements FollowersDataSource {
    public static final String TAG = FollowingsRepository.class.getSimpleName();

    private DribbbleService mService;
    Map<Integer, DribbbleFollower> mCachedFollowings;
    boolean mCacheIsDirty = false;

    private FollowingsRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static FollowingsRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void getFollowings(@Nullable String url, final int page, final LoadFollowersCallback
            callback) {
        if (mCachedFollowings == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get followings");
            Call<List<DribbbleFollower>> call = mService.getUserFollowers(DribbleConstant
                    .AUTH_TYPE +
                    DribbleConstant.CLIENT_ACCESS_TOKEN, url, page);
            call.enqueue(new Callback<List<DribbbleFollower>>() {
                @Override
                public void onResponse(Call<List<DribbbleFollower>> call,
                                       Response<List<DribbbleFollower>> response) {
                    LogUtil.i(TAG, "get shots code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleFollower> followings = response.body();
                    refreshCache(page, followings);
                    if (callback != null)
                        callback.onFollowersLoaded(followings);
                }

                @Override
                public void onFailure(Call<List<DribbbleFollower>> call, Throwable t) {
                    LogUtil.i(TAG, "get shots call executed: " + call.isExecuted() + ", url: " +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onFollowersLoaded(new ArrayList<>(mCachedFollowings.values()));
        }
    }

    public void refreshShots() {
        mCacheIsDirty = true;
    }

    private void refreshCache(int page, List<DribbbleFollower> followings) {
        if (mCachedFollowings == null) {
            mCachedFollowings = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedFollowings.clear();
        for (DribbbleFollower follower : followings) {
            mCachedFollowings.put(follower.getId(), follower);
        }
        mCacheIsDirty = false;
    }

    @Nullable
    private DribbbleFollower getFollowingWithId(int id) {
        if (mCachedFollowings == null || mCachedFollowings.isEmpty()) {
            return null;
        } else {
            return mCachedFollowings.get(id);
        }
    }

    private static class Singleton {
        private static final FollowingsRepository INSTANCE = new FollowingsRepository();
    }
}
