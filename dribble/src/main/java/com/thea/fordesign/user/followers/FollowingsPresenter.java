package com.thea.fordesign.user.followers;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.bean.DribbbleFollower;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.user.data.FollowersDataSource;
import com.thea.fordesign.user.data.FollowingsRepository;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class FollowingsPresenter implements FollowersContract.Presenter {
    public static final String TAG = FollowingsPresenter.class.getSimpleName();

    private final FollowersContract.View mFollowersView;
    private final FollowingsRepository mRepository;

    public FollowingsPresenter(FollowersContract.View followersView) {
        mFollowersView = Preconditions.checkNotNull(followersView, "followersView cannot be null");
        mRepository = FollowingsRepository.getInstance();

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
        loadFollowings(url, 1, true);
    }

    @Override
    public void loadMore(String url, int page) {
        LogUtil.i(TAG, "load more: " + page);
        loadFollowings(url, page, false);
    }

    private void loadFollowings(String url, final int page, final boolean showLoadingUI) {
        if (showLoadingUI)
            mFollowersView.setLoadingIndicator(true);

        mRepository.refreshShots();

        mRepository.getFollowings(url, page, new FollowersDataSource.LoadFollowersCallback() {
            @Override
            public void onFollowersLoaded(List<DribbbleFollower> followers) {
                if (showLoadingUI)
                    mFollowersView.setLoadingIndicator(false);
                if (page == 1)
                    mFollowersView.showFollowers(followers);
                else
                    mFollowersView.insertFollowers(followers);
            }

            @Override
            public void onDataNotAvailable() {
                if (showLoadingUI)
                    mFollowersView.setLoadingIndicator(false);
                mFollowersView.showSnack(R.string.error_load_user_followings);
            }
        });
    }


    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser, View v) {
        mFollowersView.showUserDetailsUi(requestedUser, v);
    }
}
