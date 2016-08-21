package com.thea.fordesign.shot.detail;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.bean.DribbbleUserLike;
import com.thea.fordesign.shot.data.ShotsDataSource;
import com.thea.fordesign.shot.data.ShotsRepository;
import com.thea.fordesign.util.FileUtil;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.io.File;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotDetailPresenter implements ShotDetailContract.Presenter {
    public static final String TAG = ShotDetailPresenter.class.getSimpleName();

    private final ShotDetailContract.View mDetailView;
    private final ShotsRepository mRepository;
    private final UserModel mUserModel;

    private int mShotId;
    private DribbbleShot mShot;
    private DribbbleUser mUser;

    private String mImageName;

    public ShotDetailPresenter(int shotId, DribbbleUser user, @NonNull ShotDetailContract.View detailView, @NonNull
            UserModel userModel) {
        mDetailView = Preconditions.checkNotNull(detailView, "detailView cannot be null");
        mRepository = ShotsRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mShotId = shotId;
        mUser = user;

        mDetailView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void getShot() {
        if (mShot != null)
            mDetailView.showShot(mShot);
        else
            mRepository.getShot(mUserModel.getDribbbleAccessToken(), mShotId, new ShotsDataSource
                    .GetShotCallback() {

                @Override
                public void onShotLoaded(DribbbleShot shot) {
                    if (shot != null) {
                        mShot = shot;
                        if (mShot.getUser() == null)
                            mShot.setUser(mUser);
                        mDetailView.showShot(mShot);
                    } else
                        mDetailView.showSnack(R.string.error_get_shot);
                }

                @Override
                public void onDataNotAvailable() {
                    mDetailView.showSnack(R.string.error_get_shot);
                }
            });
    }

    @Override
    public void likeShot(@NonNull DribbbleShot shot, boolean like) {
        if (like)
            likeShot(shot);
        else
            unlikeShot(shot);
    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser, android.view.View v) {
        mDetailView.showUserDetailsUi(requestedUser, v);
    }

    private void likeShot(@NonNull DribbbleShot shot) {
        mRepository.likeShot(mUserModel.getDribbbleAccessToken(), shot.getId(), new ShotsDataSource
                .LikeShotCallback() {

            @Override
            public void onSuccess(DribbbleUserLike like) {
                mShot.setLikesCount(mShot.getLikesCount() + 1);
                mDetailView.showShotLiked();
            }

            @Override
            public void onFail(int errCode, String message) {
                mDetailView.showSnack(message);
            }
        });
    }

    private void unlikeShot(@NonNull DribbbleShot shot) {
        mRepository.unlikeShot(mUserModel.getDribbbleAccessToken(), shot.getId(), new
                ShotsDataSource
                        .UnlikeShotCallback() {

                    @Override
                    public void onSuccess() {
                        mShot.setLikesCount(mShot.getLikesCount() - 1);
                        mDetailView.showShotUnliked();
                    }

                    @Override
                    public void onFail(int errCode, String message) {
                        mDetailView.showSnack(message);
                    }
                });
    }

    @Override
    public void bucketShot(@NonNull DribbbleShot shot) {
        mDetailView.showShotToBucketUi(shot.getId());
    }

    @Override
    public void shareShot(@NonNull DribbbleShot shot, int where) {
        mDetailView.showSnack("分享功能开发中");
    }

    @Override
    public void openLikers(@NonNull DribbbleShot shot) {
        mDetailView.showLikesUi(shot.getLikesUrl());
    }

    @Override
    public void openComments(@NonNull DribbbleShot shot) {
        mDetailView.showCommentsUi(shot.getId(), shot.getCommentsUrl());
    }

    @Override
    public void openBuckets(@NonNull DribbbleShot shot) {
        mDetailView.showBucketsUi(shot.getBucketsUrl());
    }

    @Override
    public void openInBrowser() {
        mDetailView.showInBrowser(mShot.getHtmlUrl());
    }

    @Override
    public void copyShotUrl(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context
                .CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, mShot.getHtmlUrl());
        clipboardManager.setPrimaryClip(clipData);
        mDetailView.showSnack(R.string.msg_shot_image_copy_link_success);
    }

    @Override
    public void shareShot() {
        mDetailView.showShareChooser(mShot.getHtmlUrl());
    }

    @RequiresPermission(allOf = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    @Override
    public void saveImageToLocal(final Context context) {
        mDetailView.showSnack(R.string.msg_shot_image_loading);
        String url = mShot.getImage();
        Observable.just(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        try {
                            File file = Glide.with(context).load(s).downloadOnly(Target
                                    .SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                            int start = s.lastIndexOf('/');
                            mImageName = s.substring(start);
                            boolean writtenToDisk = FileUtil.saveFileToStore
                                    (file, mImageName);

                            LogUtil.d(TAG, "file download successful? " +
                                    writtenToDisk);

                            return writtenToDisk;
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean written) {
                        if (written)
                            mDetailView.showSnack(R.string.msg_shot_image_load_success, R.string
                                    .action_open, new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    FileUtil.openFile(context, new File(FileUtil
                                            .DRIBBBLE_IMAGE_DIRECTORY + mImageName));
                                }
                            });
                        else mDetailView.showSnack(R.string.msg_shot_image_load_fail);
                    }
                });
    }

    @Override
    public String formatTime(String timeStr) {
        return timeStr.substring(0, timeStr.indexOf("T"));
    }

    @Override
    public void moreActions() {
        mDetailView.showMoreActionDialog(mShot);
    }

    @Override
    public void start() {
        getShot();
    }
}
