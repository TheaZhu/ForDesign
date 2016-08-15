package com.thea.fordesign.shot.detail;

import android.content.Context;
import android.support.annotation.StringRes;

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

        void showSnack(@StringRes int resId, @StringRes int actionResId, android.view.View
                .OnClickListener clickListener);
    }

    interface Presenter extends BasePresenter {

        void close();

        void copyImageUrl(Context context);

        void shareImage();

        void saveImageToLocal();
    }
}
