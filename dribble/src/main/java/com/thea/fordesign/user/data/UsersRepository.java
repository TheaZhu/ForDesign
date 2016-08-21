package com.thea.fordesign.user.data;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.bean.DribbbleUser;
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
public class UsersRepository implements UsersDataSource {
    public static final String TAG = UsersRepository.class.getSimpleName();

    private DribbbleService mService;

    Map<Integer, DribbbleUser> mCachedUsers;
    boolean mCacheIsDirty = false;

    private UsersRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static UsersRepository getInstance() {
        return Singleton.INSTANCE;
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getUsers(@NonNull String authorization, @Nullable String url, final int page, final
    LoadUsersCallback callback) {
        if (mCachedUsers == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get users");
            Call<List<DribbbleUser>> call = mService.getUsers(authorization, url, page);
            call.enqueue(new Callback<List<DribbbleUser>>() {
                @Override
                public void onResponse(Call<List<DribbbleUser>> call,
                                       Response<List<DribbbleUser>> response) {
                    LogUtil.i(TAG, "get users code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleUser> users = response.body();
                    refreshCache(page, users);
                    if (callback != null)
                        callback.onUsersLoaded(users);
                }

                @Override
                public void onFailure(Call<List<DribbbleUser>> call, Throwable t) {
                    LogUtil.i(TAG, "get users call executed: " + call.isExecuted() + ", url: " +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onUsersLoaded(new ArrayList<>(mCachedUsers.values()));
        }
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getUser(@NonNull String authorization, int userId, final GetUserCallback
            callback) {
        DribbbleUser cachedUser = getUserWithId(userId);

        // Respond immediately with cache if available
        if (cachedUser != null) {
            LogUtil.i(TAG, "get user local: " + userId);
            if (callback != null)
                callback.onUserLoaded(cachedUser);
        } else {
            LogUtil.i(TAG, "get user: " + userId);
            /*Call<DribbbleUser> call = mService.getUser(authorization, userId);
            call.enqueue(new Callback<DribbbleUser>() {
                @Override
                public void onResponse(Call<DribbbleUser> call, Response<DribbbleUser>
                        response) {
                    LogUtil.i(TAG, "get user code: " + response.code() + ", message: " + response
                            .message());
                    if (callback != null)
                        callback.onUserLoaded(response.body());
                }

                @Override
                public void onFailure(Call<DribbbleUser> call, Throwable t) {
                    LogUtil.i(TAG, "get user call executed: " + call.isExecuted() + ", url: "
                            + call
                            .request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });*/
        }

    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void saveUser(@NonNull String authorization, @NonNull DribbbleUser user) {

    }

    @Override
    public void refreshUsers() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllUsers() {
        if (mCachedUsers == null) {
            mCachedUsers = new LinkedHashMap<>();
        }
        mCachedUsers.clear();
    }

    @Override
    public void deleteUser(int userId) {
        mCachedUsers.remove(userId);
    }

    private void refreshCache(int page, List<DribbbleUser> users) {
        if (mCachedUsers == null) {
            mCachedUsers = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedUsers.clear();
        for (DribbbleUser user : users) {
            mCachedUsers.put(user.getId(), user);
        }
        mCacheIsDirty = false;
    }

    @Nullable
    private DribbbleUser getUserWithId(int id) {
        if (mCachedUsers == null || mCachedUsers.isEmpty()) {
            return null;
        } else {
            return mCachedUsers.get(id);
        }
    }

    private static class Singleton {
        private static final UsersRepository INSTANCE = new UsersRepository();
    }
}
