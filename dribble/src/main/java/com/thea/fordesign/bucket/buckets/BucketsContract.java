package com.thea.fordesign.bucket.buckets;

import android.support.annotation.StringRes;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.bucket.BucketItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface BucketsContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean visible, boolean active, @StringRes int resId, boolean enableClick);

        void setLoadingError();

        void showBuckets(List<DribbbleBucket> buckets);

        void insertBuckets(List<DribbbleBucket> buckets);

        void insertBucket(DribbbleBucket bucket);

        void removeBucket(int id);

        void updateBucket(DribbbleBucket bucket);

        void showBucketShotsUi(int bucketId);

        void showBucketEditDialog(int bucketId, String name, String description);

        void showBucketDeleteDialog(int bucketId);

    }

    interface Presenter extends BucketItemPresenter {

        void result(int requestCode, int resultCode);

        void loadBuckets();

        void loadMore(int page);

        void createNewBucket(String name, String description);

        void updateBucket(int bucketId, String name, String description);

        void deleteBucket(int bucketId);

    }
}
