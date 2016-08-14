package com.thea.fordesign.bucket.selectable;

import android.support.annotation.StringRes;

import com.thea.fordesign.base.BaseView;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.bucket.SelectableBucketItemPresenter;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface ShotToBucketsContract {

    interface View extends BaseView<Presenter> {

        void setRefreshingIndicator(boolean active);

        void setLoadingIndicator(boolean visible, boolean active, @StringRes int resId, boolean
                enableClick);

        void setLoadingError();

        void showBuckets(List<DribbbleBucket> buckets);

        void insertBuckets(List<DribbbleBucket> buckets);

        void insertBucket(DribbbleBucket bucket);

        void showItemSelected(android.view.View v, boolean selected);

    }

    interface Presenter extends SelectableBucketItemPresenter {

        void loadBuckets();

        void loadMore(int page);

        void createNewBucket(String name, String description);

    }
}
