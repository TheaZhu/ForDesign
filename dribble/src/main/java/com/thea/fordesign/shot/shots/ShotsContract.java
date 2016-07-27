package com.thea.fordesign.shot.shots;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleShot;
import com.thea.fordesign.shot.ShotItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotsContract {

    interface View extends BaseView<Presenter> {

//        void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener);

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean active, @StringRes int resId, boolean enableClick);

        void setLoadingError();

//        void setAdapter(RecyclerView.Adapter adapter);

        void showShots(List<DribbbleShot> shots);

        void insertShots(List<DribbbleShot> shots);

        void showShotDetailsUi(int shotId, String imageUrl, android.view.View v);

        void showUserDetailsUi(int userId);

//        void setShotLikeState(boolean like);

    }

    interface Presenter extends ShotItemPresenter {

        void result(int requestCode, int resultCode);

        void loadShots(String list, String sort, String timeFrame);

        void loadShots(String shotsUrl);

        void loadMore(String list, String sort, String timeFrame, int page);

        void loadMore(String shotsUrl, int page);

        void likeShot(@NonNull DribbbleShot shot, boolean like);

    }
}
