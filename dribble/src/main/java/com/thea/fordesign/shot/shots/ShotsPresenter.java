package com.thea.fordesign.shot.shots;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.shot.data.ShotsDataSource;
import com.thea.fordesign.shot.data.ShotsRepository;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotsPresenter implements ShotsContract.Presenter {
    public static final String TAG = ShotsPresenter.class.getSimpleName();

    private final ShotsContract.View mShotsView;
    private final UserModel mUserModel;

    private final ShotsRepository mRepository;

    private boolean mFirstLoad = true;

    public ShotsPresenter(@NonNull ShotsContract.View shotsView, @NonNull UserModel userModel) {
        mShotsView = Preconditions.checkNotNull(shotsView, "shotsView cannot be null");
        mRepository = ShotsRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mShotsView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadShots(String list, String sort, String timeFrame) {
        loadShots(list, sort, timeFrame, 1, true);
        mFirstLoad = false;
    }

    @Override
    public void loadShots(String shotsUrl) {
        loadShots(shotsUrl, 1, true);
        mFirstLoad = false;
    }

    @Override
    public void loadMore(String list, String sort, String timeFrame, int page) {
        LogUtil.i(TAG, "load more: " + page);
        loadShots(list, sort, timeFrame, page, false);
    }

    @Override
    public void loadMore(String shotsUrl, int page) {
        LogUtil.i(TAG, "load more: " + page);
        loadShots(shotsUrl, page, false);
    }

    private void loadShots(String list, String sort, String timeFrame, final int page, final boolean
            showLoadingUI) {
        if (showLoadingUI)
            mShotsView.setLoadingIndicator(true);

        mRepository.refreshShots();

        mRepository.getShots(mUserModel.getDribbbleAccessToken(), list, sort, timeFrame, null,
                page, 12, new ShotsDataSource.LoadShotsCallback() {
                    @Override
                    public void onShotsLoaded(List<DribbbleShot> shots) {
                        if (showLoadingUI)
                            mShotsView.setLoadingIndicator(false);
                        if (page == 1)
                            mShotsView.showShots(shots);
                        else
                            mShotsView.insertShots(shots);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (showLoadingUI)
                            mShotsView.setLoadingIndicator(false);
                        mShotsView.showSnack(R.string.error_load_shots);
                    }
                });
    }

    private void loadShots(String url, final int page, final boolean showLoadingUI) {
        if (showLoadingUI)
            mShotsView.setLoadingIndicator(true);

        mRepository.refreshShots();

        mRepository.getShots(mUserModel.getDribbbleAccessToken(), url, page, new ShotsDataSource
                .LoadShotsCallback() {
            @Override
            public void onShotsLoaded(List<DribbbleShot> shots) {
                if (showLoadingUI)
                    mShotsView.setLoadingIndicator(false);
                if (page == 1)
                    mShotsView.showShots(shots);
                else
                    mShotsView.insertShots(shots);
            }

            @Override
            public void onDataNotAvailable() {
                if (showLoadingUI)
                    mShotsView.setLoadingIndicator(false);
                mShotsView.showSnack(R.string.error_load_shots);
            }
        });
    }

    @Override
    public String formatTime(String timeStr) {
        return timeStr.substring(0, timeStr.indexOf("T"));
    }

    @Override
    public void openShotDetails(@NonNull DribbbleShot requestedShot, View v) {
        mShotsView.showShotDetailsUi(requestedShot.getId(), requestedShot.getImages().getNormal()
                , v);
    }

    @Override
    public void likeShot(@NonNull DribbbleShot shot, boolean like) {
        mShotsView.showSnack("登录功能开发中");
    }

    @Override
    public void start() {
        LogUtil.i(TAG, "start");
    }
}
