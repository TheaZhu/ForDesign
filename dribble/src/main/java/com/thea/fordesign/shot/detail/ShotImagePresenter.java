package com.thea.fordesign.shot.detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.thea.fordesign.R;
import com.thea.fordesign.util.FileUtil;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.io.File;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotImagePresenter implements ShotImageContract.Presenter {
    public static final String TAG = ShotImagePresenter.class.getSimpleName();

    private final ShotImageContract.View mView;
    private String mUrl;

    private String mImageName;

    public ShotImagePresenter(@NonNull ShotImageContract.View view, String url) {
        mView = Preconditions.checkNotNull(view, "imageView cannot be null");
        mUrl = url;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void close() {
        mView.showPrevious();
    }

    @Override
    public void copyImageUrl(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context
                .CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, mUrl);
        clipboardManager.setPrimaryClip(clipData);
        mView.showSnack(R.string.msg_shot_image_copy_link_success);
    }

    @Override
    public void shareImage() {
        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File file = new File(FileUtil.DRIBBBLE_IMAGE_DIRECTORY + mImageName);

                subscriber.onNext(file);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "error");
                        e.printStackTrace();
                        mView.showSnack(R.string.msg_shot_image_share_fail);
                    }

                    @Override
                    public void onNext(File file) {
                        if (file != null)
                            mView.showShareChooser(file);
                        else mView.showSnack(R.string.msg_shot_image_share_fail_empty);
                    }
                });
    }

    @Override
    public void saveImageToLocal() {
        mView.showSnack(R.string.msg_shot_image_loading);
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    File file = Glide.with(mView.getContext()).load(mUrl).downloadOnly(Target
                            .SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                    int start = mUrl.lastIndexOf('/');
                    mImageName = mUrl.substring(start);
                    boolean writtenToDisk = FileUtil.saveFileToStore
                            (file, mUrl.substring(start));

                    LogUtil.d(TAG, "file download successful? " +
                            writtenToDisk);

                    subscriber.onNext(writtenToDisk);
                    subscriber.onCompleted();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "error");
                        e.printStackTrace();
                        mView.showSnack(R.string.msg_shot_image_load_fail);
                    }

                    @Override
                    public void onNext(Boolean written) {
                        if (written)
                            mView.showSnack(R.string.msg_shot_image_load_success);
                        else mView.showSnack(R.string.msg_shot_image_load_fail);
                    }
                });


        /*LogUtil.i(TAG, "url: " + mUrl);

        mService.downloadImageFile(mUrl)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        int start = mUrl.lastIndexOf('/');
                        boolean writtenToDisk = FileUtil.saveFileToStore
                                (responseBody, mUrl.substring(start));

                        responseBody.close();
                        LogUtil.d(TAG, "file download successful? " +
                                writtenToDisk);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "error");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        responseBody.close();
                    }
                });*/
    }
}
