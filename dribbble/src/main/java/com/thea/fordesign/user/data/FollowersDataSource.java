package com.thea.fordesign.user.data;

import com.thea.fordesign.bean.DribbbleFollower;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface FollowersDataSource {

    interface LoadFollowersCallback {

        void onFollowersLoaded(List<DribbbleFollower> followers);

        void onDataNotAvailable();
    }

    interface GetFollowerCallback {

        void onFollowerLoaded(DribbbleFollower follower);

        void onDataNotAvailable();
    }

}
