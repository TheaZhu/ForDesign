package com.thea.fordesign.bucket.buckets;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.bucket.BucketItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface BucketsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showBuckets(List<DribbbleBucket> buckets);

        void insertBuckets(List<DribbbleBucket> buckets);

        void showBucketShotsUi(int bucketId);

    }

    interface Presenter extends BucketItemPresenter {

        void result(int requestCode, int resultCode);

        void loadBuckets();

        void loadMore(int page);

    }
}
