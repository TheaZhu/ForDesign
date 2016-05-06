package com.thea.fordesign.shot.detail;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotDetailContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void loadShot(int shotId);
    }
}
