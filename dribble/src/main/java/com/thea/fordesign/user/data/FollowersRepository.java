package com.thea.fordesign.user.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.DribbbleService;
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
public class FollowersRepository implements FollowersDataSource {
    public static final String TAG = FollowersRepository.class.getSimpleName();

    private DribbbleService mService;
    Map<Integer, DribbbleFollower> mCachedFollowers;
    boolean mCacheIsDirty = false;

    private FollowersRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static FollowersRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void getFollowers(@NonNull String authorization, @Nullable String url, final int page,
                             final LoadFollowersCallback callback) {
        if (mCachedFollowers == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get followings");
            Call<List<DribbbleFollower>> call = mService.getUserFollowers(authorization, url, page);
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
            callback.onFollowersLoaded(new ArrayList<>(mCachedFollowers.values()));
        }
    }

    public void refreshShots() {
        mCacheIsDirty = true;
    }

    private void refreshCache(int page, List<DribbbleFollower> followings) {
        if (mCachedFollowers == null) {
            mCachedFollowers = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedFollowers.clear();
        for (DribbbleFollower follower : followings) {
            mCachedFollowers.put(follower.getId(), follower);
        }
        mCacheIsDirty = false;
    }

    @Nullable
    private DribbbleFollower getFollowerWithId(int id) {
        if (mCachedFollowers == null || mCachedFollowers.isEmpty()) {
            return null;
        } else {
            return mCachedFollowers.get(id);
        }
    }

    private static class Singleton {
        private static final FollowersRepository INSTANCE = new FollowersRepository();
    }
}
