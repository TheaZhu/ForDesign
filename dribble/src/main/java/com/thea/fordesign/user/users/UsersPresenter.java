package com.thea.fordesign.user.users;

import android.support.annotation.NonNull;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.user.data.UsersDataSource;
import com.thea.fordesign.user.data.UsersRepository;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class UsersPresenter implements UsersContract.Presenter {
    public static final String TAG = UsersPresenter.class.getSimpleName();

    private final UsersContract.View mUsersView;
    private final UserModel mUserModel;
    private String mUsersUrl;

    private final UsersRepository mRepository;

    public UsersPresenter(UsersContract.View usersView, String usersUrl, @NonNull UserModel
            userModel) {
        mUsersView = Preconditions.checkNotNull(usersView, "usersView cannot be null");
        mRepository = UsersRepository.getInstance();
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mUsersUrl = usersUrl;

        mUsersView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void loadUsers() {
        mUsersView.setRefreshingIndicator(true);
        mRepository.refreshUsers();

        mRepository.getUsers(mUserModel.getDribbbleAccessToken(), mUsersUrl, 1, new
                UsersDataSource.LoadUsersCallback() {
                    @Override
                    public void onUsersLoaded(List<DribbbleUser> users) {
                        mUsersView.setRefreshingIndicator(false);
                        mUsersView.showUsers(users);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mUsersView.setRefreshingIndicator(false);
                        mUsersView.showSnack(R.string.error_load_users);
                    }
                });
    }

    @Override
    public void loadMore(int page) {
        LogUtil.i(TAG, "load more: " + page);
        mUsersView.setLoadingIndicator(true, true, R.string.loading, false);
        mRepository.refreshUsers();

        mRepository.getUsers(mUserModel.getDribbbleAccessToken(), mUsersUrl, page, new
                UsersDataSource.LoadUsersCallback() {
                    @Override
                    public void onUsersLoaded(List<DribbbleUser> users) {
                        mUsersView.setLoadingIndicator(false, false, R.string.loading, false);
                        mUsersView.insertUsers(users);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mUsersView.setLoadingIndicator(true, false,
                                R.string.loading_error, true);
                        mUsersView.setLoadingError();
                    }
                });
    }

    @Override
    public void openUserDetails(@NonNull DribbbleUser requestedUser, View v) {
        mUsersView.showUserDetailsUi(requestedUser, v);
    }
}
