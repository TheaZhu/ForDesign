package com.thea.fordesign.wrapper;

import android.support.annotation.NonNull;

import com.thea.fordesign.config.DribbbleConstant;
import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.util.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class DribbbleCommWrapper {
    public static final String TAG = DribbbleCommWrapper.class.getSimpleName();

    private DribbbleService mService;

    private DribbbleCommWrapper() {
        mService = new DribbbleService.Builder().create();
    }

    public static DribbbleCommWrapper getInstance() {
        return Singleton.INSTANCE;
    }

    public void deleteShot(@NonNull String authorization, int shotId, final DeleteShotCallback callback) {
        Call<Void> call = mService.deleteShot(authorization, shotId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                String message = response.message();
                LogUtil.i(TAG, "delete shot code: " + code + ", message: " + message);

                if (callback == null)
                    return;
                if (code == DribbbleConstant.CODE_NO_CONTENT)
                    callback.onSuccess();
                else
                    callback.onFail(code, message);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LogUtil.i(TAG, "delete shots call executed: " + call.isExecuted() +
                        ", url: " + call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFail(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    public void checkFollowingUser(@NonNull String authorization, int userId,
                                   final CheckFollowingUserCallback callback) {
        Call<Void> call = mService.checkIfFollowing(authorization, userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                String message = response.message();
                LogUtil.i(TAG, "check following user code: " + code + ", message: " + message);

                if (callback == null)
                    return;
                if (code == DribbbleConstant.CODE_NO_CONTENT)
                    callback.onSuccess(true);
                else if (code == DribbbleConstant.CODE_NOT_FOUND)
                    callback.onSuccess(false);
                else
                    callback.onFail(code, message);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LogUtil.i(TAG, "check following user call executed: " + call.isExecuted() +
                        ", url: " + call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFail(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    public void followUser(@NonNull String authorization, int userId,
                           final FollowUserCallback callback) {
        Call<String> call = mService.followUser(authorization, userId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int code = response.code();
                String message = response.message();
                LogUtil.i(TAG, "follow user code: " + code + ", message: " + message);
                LogUtil.i(TAG, "body: " + response.body());

                if (callback == null)
                    return;
                if (code == DribbbleConstant.CODE_NO_CONTENT)
                    callback.onSuccess();
                else
                    callback.onFail(code, message);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.i(TAG, "follow user call executed: " + call.isExecuted() +
                        ", url: " + call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFail(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    public void unfollowUser(@NonNull String authorization, int userId,
                           final UnfollowUserCallback callback) {
        Call<Void> call = mService.unfollowUser(authorization, userId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                String message = response.message();
                LogUtil.i(TAG, "unfollow user code: " + code + ", message: " + message);

                if (callback == null)
                    return;
                if (code == DribbbleConstant.CODE_NO_CONTENT)
                    callback.onSuccess();
                else
                    callback.onFail(code, message);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LogUtil.i(TAG, "unfollow user call executed: " + call.isExecuted() +
                        ", url: " + call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFail(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    public interface DeleteShotCallback {

        void onSuccess();

        void onFail(int errCode, String message);

    }

    public interface CheckFollowingUserCallback {

        void onSuccess(boolean following);

        void onFail(int errCode, String message);

    }

    public interface FollowUserCallback {

        void onSuccess();

        void onFail(int errCode, String message);

    }

    public interface UnfollowUserCallback {

        void onSuccess();

        void onFail(int errCode, String message);

    }

    private static class Singleton {
        private static final DribbbleCommWrapper INSTANCE = new DribbbleCommWrapper();
    }
}
