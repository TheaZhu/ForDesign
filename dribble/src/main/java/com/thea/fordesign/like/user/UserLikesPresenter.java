package com.thea.fordesign.like.user;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUserLike;
import com.thea.fordesign.like.data.UserLikesDataSource;
import com.thea.fordesign.like.data.UserLikesRepository;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UserLikesPresenter implements UserLikesContract.Presenter {
    public static final String TAG = UserLikesPresenter.class.getSimpleName();

    private final UserLikesContract.View mLikesView;
    private final UserModel mUserModel;

    private final UserLikesRepository mRepository;

    public UserLikesPresenter(@NonNull UserLikesContract.View likesView, @NonNull UserModel userModel) {
        mLikesView = Preconditions.checkNotNull(likesView, "likesView cannot be null");
        mRepository = UserLikesRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mLikesView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void loadLikes(String likesUrl) {
        loadLikes(likesUrl, 1, true, false);
    }

    @Override
    public void loadMore(String likesUrl, int page) {
        LogUtil.i(TAG, "load more: " + page);
        loadLikes(likesUrl, page, false, true);
    }

    private void loadLikes(String url, final int page, final boolean showLoadingUI, final boolean
            isLoadMore) {
        if (showLoadingUI)
            mLikesView.setRefreshingIndicator(true);

        if (isLoadMore)
            mLikesView.setLoadingIndicator(true, true, R.string.loading, false);

        mRepository.refreshLikes();

        mRepository.getLikes(mUserModel.getDribbbleAccessToken(), url, page, new UserLikesDataSource.LoadLikesCallback() {

            @Override
            public void onLikesLoaded(List<DribbbleUserLike> likes) {
                if (showLoadingUI)
                    mLikesView.setRefreshingIndicator(false);
                if (isLoadMore)
                    mLikesView.setLoadingIndicator(false, false, R.string.loading, false);
                if (page == 1)
                    mLikesView.showLikes(likes);
                else
                    mLikesView.insertLikes(likes);
            }

            @Override
            public void onDataNotAvailable() {
                if (showLoadingUI) {
                    mLikesView.setRefreshingIndicator(false);
                    mLikesView.showSnack(R.string.error_load_user_likes);
                }
                if (isLoadMore) {
                    mLikesView.setLoadingIndicator(true, false,
                            R.string.loading_error, true);
                    mLikesView.setLoadingError();
                }

            }
        });
    }

    @Override
    public String formatTime(String timeStr) {
        return timeStr.substring(0, timeStr.indexOf("T"));
    }

    @Override
    public void openShotDetails(@NonNull DribbbleShot requestedShot, View v) {
        mLikesView.showShotDetailsUi(requestedShot.getId(), requestedShot.getImages().getNormal(), v);
    }

    @Override
    public void start() {
        LogUtil.i(TAG, "start");
    }
}
