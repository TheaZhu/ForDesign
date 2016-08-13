package com.thea.fordesign.shot.detail;

import android.content.Context;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;

import java.io.File;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotImageContract {

    interface View extends BaseView<Presenter> {

        void showPrevious();

        void showShareChooser(File file);

        Context getContext();
    }

    interface Presenter extends BasePresenter {

        void close();

        void copyImageUrl(Context context);

        void shareImage();

        void saveImageToLocal();
    }
}
