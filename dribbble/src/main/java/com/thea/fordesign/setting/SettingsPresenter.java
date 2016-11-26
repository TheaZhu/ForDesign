package com.thea.fordesign.setting;

import com.thea.fordesign.MainActivity;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.util.Preconditions;

/**
 * @author Thea (theazhu0321@gmail.com)
 */

public class SettingsPresenter implements SettingsContract.Presenter {
    public static final String TAG = SettingsPresenter.class.getSimpleName();

    private final SettingsContract.View mView;
    private final UserModel mUserModel;

    public SettingsPresenter(SettingsContract.View view, UserModel userModel) {
        mView = Preconditions.checkNotNull(view, "view cannot be null");
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
    }

    @Override
    public void openAbout() {
        mView.showAboutUi();
    }

    @Override
    public void signUp() {
        mUserModel.setDribbbleUserAccessToken(null);
        mUserModel.setUserSignIn(false);
        mView.showPrevious(MainActivity.RESULT_SIGN_OUT);
    }

    @Override
    public void start() {
    }
}
