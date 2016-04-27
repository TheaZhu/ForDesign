package com.thea.fordesign.home;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface HomeContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void test();
    }
}
