package com.thea.fordesign.shot.shots;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.bean.DribbbleUser;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotsContract {

    interface View extends BaseView<Presenter> {

        void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener);

        void setLoadingIndicator(boolean active);

        void showShots(List<DribbbleShot> shots);

        void showShotDetailsUi(int shotId);

        void showUserDetailsUi(int userId);

//        void setShotLikeState(boolean like);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadShots(boolean forceUpdate);

        void openShotDetails(@NonNull DribbbleShot requestedShot);

        void openUserDetails(@NonNull DribbbleUser requestedUser);

        void likeShot(@NonNull DribbbleShot shot, boolean like);

    }
}
