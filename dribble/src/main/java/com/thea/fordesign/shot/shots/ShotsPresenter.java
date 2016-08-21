package com.thea.fordesign.shot.shots;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUser;
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

    private DribbbleUser mUser = null;

    public ShotsPresenter(@NonNull ShotsContract.View shotsView, @NonNull UserModel userModel) {
        mShotsView = Preconditions.checkNotNull(shotsView, "shotsView cannot be null");
        mRepository = ShotsRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mShotsView.setPresenter(this);
    }

    public ShotsPresenter(@NonNull ShotsContract.View shotsView, @NonNull UserModel userModel, DribbbleUser user) {
        mShotsView = Preconditions.checkNotNull(shotsView, "shotsView cannot be null");
        mRepository = ShotsRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mUser = user;

        mShotsView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadShots(String list, String sort, String timeFrame) {
        mShotsView.setRefreshingIndicator(true);

        mRepository.refreshShots();

        mRepository.getShots(mUserModel.getDribbbleAccessToken(), list, sort, timeFrame, null,
                1, 12, new ShotsDataSource.LoadShotsCallback() {
                    @Override
                    public void onShotsLoaded(List<DribbbleShot> shots) {
                        mShotsView.setRefreshingIndicator(false);
                        mShotsView.hideEmptyLayout();
                        mShotsView.showShots(shots);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mShotsView.setRefreshingIndicator(false);
                        mShotsView.showSnack(R.string.error_load_shots);
                        mShotsView.showEmptyLayout(R.string.msg_empty_data);
                    }
                });
    }

    @Override
    public void loadShots(String shotsUrl) {
        mShotsView.setRefreshingIndicator(true);

        mRepository.refreshShots();

        mRepository.getShots(mUserModel.getDribbbleAccessToken(), shotsUrl, 1, new ShotsDataSource
                .LoadShotsCallback() {
            @Override
            public void onShotsLoaded(List<DribbbleShot> shots) {
                mShotsView.setRefreshingIndicator(false);
                mShotsView.hideEmptyLayout();
                mShotsView.showShots(shots);
            }

            @Override
            public void onDataNotAvailable() {
                mShotsView.setRefreshingIndicator(false);
                mShotsView.showSnack(R.string.error_load_shots);
                mShotsView.showEmptyLayout(R.string.msg_empty_data);
            }
        });
    }

    @Override
    public void loadMore(String list, String sort, String timeFrame, int page) {
        LogUtil.i(TAG, "load more: " + page);
        mShotsView.setLoadingIndicator(true, true, R.string.loading, false);

        mRepository.refreshShots();

        mRepository.getShots(mUserModel.getDribbbleAccessToken(), list, sort, timeFrame, null,
                page, 12, new ShotsDataSource.LoadShotsCallback() {
                    @Override
                    public void onShotsLoaded(List<DribbbleShot> shots) {
                        mShotsView.setLoadingIndicator(false, false, R.string.loading, false);
                        mShotsView.insertShots(shots);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mShotsView.setLoadingIndicator(true, false, R.string.loading_error,
                                true);
                        mShotsView.setLoadingError();
                    }
                });
    }

    @Override
    public void loadMore(String shotsUrl, int page) {
        LogUtil.i(TAG, "load more: " + page);
        mShotsView.setLoadingIndicator(true, true, R.string.loading, false);

        mRepository.refreshShots();

        mRepository.getShots(mUserModel.getDribbbleAccessToken(), shotsUrl, page, new
                ShotsDataSource
                .LoadShotsCallback() {
            @Override
            public void onShotsLoaded(List<DribbbleShot> shots) {
                mShotsView.setLoadingIndicator(false, false, R.string.loading, false);
                mShotsView.insertShots(shots);
            }

            @Override
            public void onDataNotAvailable() {
                mShotsView.setLoadingIndicator(true, false, R.string.loading_error, true);
                mShotsView.setLoadingError();
            }
        });
    }

    @Override
    public String formatTime(String timeStr) {
        return timeStr.substring(0, timeStr.indexOf("T"));
    }

    @Override
    public void openShotDetails(@NonNull DribbbleShot requestedShot, View v) {
        mShotsView.showShotDetailsUi(requestedShot.getId(), requestedShot.getImage(), mUser, v);
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
