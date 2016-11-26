package com.thea.fordesign.like.data;

import com.thea.fordesign.bean.DribbbleShotLike;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotLikesDataSource {

    interface LoadLikesCallback {

        void onShotLikesLoaded(List<DribbbleShotLike> likes);

        void onDataNotAvailable();
    }

    interface GetLikeCallback {

        void onShotLikeLoaded(DribbbleShotLike like);

        void onDataNotAvailable();
    }

}
