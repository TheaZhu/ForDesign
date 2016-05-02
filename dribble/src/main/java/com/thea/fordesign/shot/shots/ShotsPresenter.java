package com.thea.fordesign.shot.shots;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import com.thea.fordesign.R;
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

    private final ShotsRepository mRepository;

    private boolean mFirstLoad = true;

    public ShotsPresenter(ShotsContract.View shotsView) {
        mShotsView = Preconditions.checkNotNull(shotsView, "shotsView cannot be null");
        mRepository = ShotsRepository.getInstance();
        mShotsView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadShots(boolean forceUpdate) {
        loadShots(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadShots(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI)
            mShotsView.setLoadingIndicator(true);

        mRepository.refreshShots();

        mRepository.getShots(new ShotsDataSource.LoadShotsCallback() {
            @Override
            public void onShotsLoaded(List<DribbbleShot> shots) {
                if (showLoadingUI)
                    mShotsView.setLoadingIndicator(false);
                mShotsView.showShots(shots);
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
    public void openShotDetails(@NonNull DribbbleShot requestedShot) {
        mShotsView.showShotDetailsUi(requestedShot.getId());
    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser) {
        mShotsView.showUserDetailsUi(requestedUser.getId());
    }

    @Override
    public void likeShot(@NonNull DribbbleShot shot, boolean like) {
        mShotsView.showSnack("登录功能开发中");
    }

    @Override
    public void start() {
        LogUtil.i(TAG, "start");
        mShotsView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadShots(true);
            }
        });
        loadShots(true);
    }
}
