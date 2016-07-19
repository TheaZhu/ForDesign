package com.thea.fordesign.shot.detail;

import android.support.annotation.NonNull;

import com.thea.fordesign.R;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.shot.data.ShotsDataSource;
import com.thea.fordesign.shot.data.ShotsRepository;
import com.thea.fordesign.util.Preconditions;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotDetailPresenter implements ShotDetailContract.Presenter {

    private final ShotDetailContract.View mDetailView;
    private final ShotsRepository mRepository;

    private int mShotId;
    private DribbbleShot mShot;

    public ShotDetailPresenter(int shotId, ShotDetailContract.View detailView) {
        mDetailView = Preconditions.checkNotNull(detailView, "detailView cannot be null");
        mRepository = ShotsRepository.getInstance();
        mShotId = shotId;

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
            mRepository.getShot(mShotId, new ShotsDataSource.GetShotCallback() {

                @Override
                public void onShotLoaded(DribbbleShot shot) {
                    if (shot != null) {
                        mShot = shot;
                        mDetailView.showShot(shot);
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
    public void openUserDetails(@NonNull DribbbleUser requestedUser, android.view.View v) {
        mDetailView.showUserDetailsUi(requestedUser, v);
    }

    @Override
    public void likeShot(@NonNull DribbbleShot shot) {
        mDetailView.showSnack("登录功能开发中");
    }

    @Override
    public void shareShot(@NonNull DribbbleShot shot, int where) {
        mDetailView.showSnack("分享功能开发中");
    }

    @Override
    public void openViewers(@NonNull DribbbleShot shot) {

    }

    @Override
    public void openLikers(@NonNull DribbbleShot shot) {

    }

    @Override
    public void openCommenters(@NonNull DribbbleShot shot) {

    }

    @Override
    public void openBuckets(@NonNull DribbbleShot shot) {

    }

    @Override
    public String formatTime(String timeStr) {
        return timeStr.substring(0, timeStr.indexOf("T"));
    }

    @Override
    public void start() {
        getShot();
    }
}
