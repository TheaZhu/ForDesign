package com.thea.fordesign.setting.about;

import android.content.Context;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;

/**
 * @author Thea (theazhu0321@gmail.com)
 */

public interface AboutContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void checkUpdate();

        void openVersionIllustration();

        void openFeedback();

        void openStar();

        void openCopyrightInformation(Context context);
    }
}
