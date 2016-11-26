package com.thea.fordesign.setting;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;

/**
 * @author Thea (theazhu0321@gmail.com)
 */

public interface SettingsContract {

    interface View extends BaseView<Presenter> {

        void showAboutUi();

        void showPrevious(int resultCode);

    }

    interface Presenter extends BasePresenter {

        void openAbout();

        void signUp();
    }
}
