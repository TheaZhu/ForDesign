package com.thea.fordesign.shot.detail;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.thea.fordesign.FileDownloadService;
import com.thea.fordesign.util.FileUtil;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotImagePresenter implements ShotImageContract.Presenter {
    public static final String TAG = ShotImagePresenter.class.getSimpleName();

    private final ShotImageContract.View mView;
    private FileDownloadService mService;
    private String mUrl;

    public ShotImagePresenter(@NonNull ShotImageContract.View view, String url) {
        mView = Preconditions.checkNotNull(view, "imageView cannot be null");
        mUrl = url;
        mService = new FileDownloadService.Builder().create();

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        saveImageToLocal();
    }

    @Override
    public void close() {
        mView.showPrevious();
    }

    @Override
    public void copyImage() {

    }

    @Override
    public void shareImage() {

    }

    @Override
    public void saveImageToLocal() {
        /*try {
            File file = Glide.with(mView.getContext()).load(mUrl).downloadOnly(Target
                    .SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

        LogUtil.i(TAG, "url: " + mUrl);
        new AsyncTask<Void, Long, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Call<ResponseBody> call = mService.downloadImageFile(mUrl);

                call.enqueue(new Callback<ResponseBody>() {
                                 @Override
                                 public void onResponse(Call<ResponseBody> call,
                                                        Response<ResponseBody> response) {
                                     if (response.isSuccessful()) {
                                         LogUtil.d(TAG, "server contacted and has file");

                                         int start = mUrl.lastIndexOf('/');
                                         boolean writtenToDisk = FileUtil.saveFileToStore
                                                 (response.body(), mUrl.substring(start));

                                         LogUtil.d(TAG, "file download was a success? " +
                                                 writtenToDisk);
                                     } else {
                                         LogUtil.d(TAG, "server contact failed");
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<ResponseBody> call, Throwable t) {
                                     LogUtil.e(TAG, "error");
                                     t.printStackTrace();
                                 }
                             });
                return null;
            }
        }.execute();
    }
}
