package com.thea.fordesign.like.shot;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleShotLike;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.like.data.ShotLikesDataSource;
import com.thea.fordesign.like.data.ShotLikesRepository;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotLikesPresenter implements ShotLikesContract.Presenter {
    public static final String TAG = ShotLikesPresenter.class.getSimpleName();

    private final ShotLikesContract.View mLikesView;
    private final UserModel mUserModel;
    private String mLikeUrl;

    private final ShotLikesRepository mRepository;

    public ShotLikesPresenter(@NonNull ShotLikesContract.View likesView, String likeUrl, @NonNull
    UserModel
            userModel) {
        mLikesView = Preconditions.checkNotNull(likesView, "likesView cannot be null");
        mLikeUrl = likeUrl;
        mRepository = ShotLikesRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mLikesView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void loadLikes() {
        mLikesView.setRefreshingIndicator(true);

        mRepository.refreshLikes();

        mRepository.getShotLikes(mUserModel.getDribbbleAccessToken(), mLikeUrl, 1, new
                ShotLikesDataSource.LoadLikesCallback() {

                    @Override
                    public void onShotLikesLoaded(List<DribbbleShotLike> likes) {
                        mLikesView.setRefreshingIndicator(false);
                        mLikesView.showLikes(likes);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mLikesView.setRefreshingIndicator(false);
                        mLikesView.showSnack(R.string.error_load_user_likes);

                    }
                });
    }

    @Override
    public void loadMore(int page) {
        mLikesView.setLoadingIndicator(true, R.string.loading, false);

        mRepository.refreshLikes();

        mRepository.getShotLikes(mUserModel.getDribbbleAccessToken(), mLikeUrl, page, new
                ShotLikesDataSource.LoadLikesCallback() {

                    @Override
                    public void onShotLikesLoaded(List<DribbbleShotLike> likes) {
                        mLikesView.setLoadingIndicator(false, R.string.loading, false);
                        mLikesView.insertLikes(likes);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mLikesView.setLoadingIndicator(false, R.string.loading_error, true);
                        mLikesView.setLoadingError();
                    }
                });
    }

    @Override
    public void start() {
        LogUtil.i(TAG, "start");
    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser, View v) {
        mLikesView.showUserDetailsUi(requestedUser, v);
    }
}
