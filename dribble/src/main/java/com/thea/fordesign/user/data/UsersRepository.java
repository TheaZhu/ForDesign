package com.thea.fordesign.user.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.DribbbleConstant;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.util.LogUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UsersRepository implements UsersDataSource {
    public static final String TAG = UsersRepository.class.getSimpleName();

    private static UsersRepository sInstance = null;

    private DribbbleService mService;

    //    List<DribbbleShot> mCachedShots;
    Map<Integer, DribbbleUser> mCachedUsers;
    boolean mCacheIsDirty = false;

    private UsersRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static UsersRepository getInstance() {
        if (sInstance == null) {
            synchronized (UsersRepository.class) {
                if (sInstance == null)
                    sInstance = new UsersRepository();
            }
        }
        return sInstance;
    }

    @Override
    public void getUser(int userId, final GetUserCallback callback) {
        DribbbleUser cachedUser = getUserWithId(userId);

        // Respond immediately with cache if available
        if (cachedUser != null) {
            LogUtil.i(TAG, "get user local: " + userId);
            if (callback != null)
                callback.onUserLoaded(cachedUser);
        } else {
            LogUtil.i(TAG, "get user: " + userId);
            Call<DribbbleUser> call = mService.getUser(DribbbleConstant.AUTH_TYPE +
                    DribbbleConstant.CLIENT_ACCESS_TOKEN, userId);
            call.enqueue(new Callback<DribbbleUser>() {
                @Override
                public void onResponse(Call<DribbbleUser> call, Response<DribbbleUser> response) {
                    LogUtil.i(TAG, "get user code: " + response.code() + ", message: " + response
                            .message());
                    if (callback != null)
                        callback.onUserLoaded(response.body());
                }

                @Override
                public void onFailure(Call<DribbbleUser> call, Throwable t) {
                    LogUtil.i(TAG, "get user call executed: " + call.isExecuted() + ", url: " + call
                            .request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        }
    }

    @Override
    public void saveUser(@NonNull DribbbleUser user) {

    }

    @Override
    public void deleteUser(int userId) {

    }

    @Nullable
    private DribbbleUser getUserWithId(int id) {
        if (mCachedUsers == null || mCachedUsers.isEmpty()) {
            return null;
        } else {
            return mCachedUsers.get(id);
        }
    }
}
