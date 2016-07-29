package com.thea.fordesign.user.followers;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleFollower;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.user.data.FollowersDataSource;
import com.thea.fordesign.user.data.FollowersRepository;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class FollowersPresenter implements FollowersContract.Presenter {
    public static final String TAG = FollowersPresenter.class.getSimpleName();

    private final FollowersContract.View mFollowersView;
    private final UserModel mUserModel;

    private final FollowersRepository mRepository;

    public FollowersPresenter(FollowersContract.View followersView, @NonNull UserModel userModel) {
        mFollowersView = Preconditions.checkNotNull(followersView, "followersView cannot be null");
        mRepository = FollowersRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");

        mFollowersView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void loadFollowers(String url) {
        mFollowersView.setRefreshingIndicator(true);
        mRepository.refreshFollowers();

        mRepository.getFollowers(mUserModel.getDribbbleAccessToken(), url, 1, new
                FollowersDataSource.LoadFollowersCallback() {
                    @Override
                    public void onFollowersLoaded(List<DribbbleFollower> followers) {
                        mFollowersView.setRefreshingIndicator(false);
                        mFollowersView.showFollowers(followers);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mFollowersView.setRefreshingIndicator(false);
                        mFollowersView.showSnack(R.string.error_load_user_followings);
                    }
                });
    }

    @Override
    public void loadMore(String url, int page) {
        LogUtil.i(TAG, "load more: " + page);
        mFollowersView.setLoadingIndicator(true, R.string.loading, false);
        mRepository.refreshFollowers();

        mRepository.getFollowers(mUserModel.getDribbbleAccessToken(), url, page, new
                FollowersDataSource.LoadFollowersCallback() {
                    @Override
                    public void onFollowersLoaded(List<DribbbleFollower> followers) {
                        mFollowersView.setLoadingIndicator(false, R.string.loading, false);
                        mFollowersView.insertFollowers(followers);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mFollowersView.setLoadingIndicator(false, R.string.loading_error, true);
                        mFollowersView.setLoadingError();
                    }
                });
    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser, View v) {
        mFollowersView.showUserDetailsUi(requestedUser, v);
    }
}
