package com.thea.fordesign.shot.detail;

import android.content.Context;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotImageContract {

    interface View extends BaseView<Presenter> {

        void showPrevious();

        Context getContext();
    }

    interface Presenter extends BasePresenter {

        void close();

        void copyImage();

        void shareImage();

        void saveImageToLocal();
    }
}
