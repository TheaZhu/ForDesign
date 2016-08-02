package com.thea.fordesign.wrapper;

import android.support.annotation.NonNull;

import com.thea.fordesign.DribbbleConstant;
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

    private static DribbbleCommWrapper ourInstance = new DribbbleCommWrapper();

    public static DribbbleCommWrapper getInstance() {
        return ourInstance;
    }

    private DribbbleService mService;

    private DribbbleCommWrapper() {
        mService = new DribbbleService.Builder().create();
    }

    public void deleteShot(@NonNull String authorization, int shotId, final DeleteShotCallback callback) {
        Call<Response> call = mService.deleteShot(authorization, shotId);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
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
            public void onFailure(Call<Response> call, Throwable t) {
                LogUtil.i(TAG, "delete shots call executed: " + call.isExecuted() +
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
}
