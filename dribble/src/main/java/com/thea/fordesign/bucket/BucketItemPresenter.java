package com.thea.fordesign.bucket;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.bean.DribbbleBucket;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface BucketItemPresenter extends BasePresenter {

    String formatTime(String timeStr);

    void openBucketShots(int bucketId);

    void editBucket(DribbbleBucket bucket);

    void deleteBucket(DribbbleBucket bucket);
}
