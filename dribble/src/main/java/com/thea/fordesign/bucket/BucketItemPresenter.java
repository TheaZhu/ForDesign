package com.thea.fordesign.bucket;

import com.thea.fordesign.base.BasePresenter;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface BucketItemPresenter extends BasePresenter {

    String formatTime(String timeStr);

    void openBucketShots(int bucketId);
}
