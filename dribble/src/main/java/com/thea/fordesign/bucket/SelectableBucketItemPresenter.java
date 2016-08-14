package com.thea.fordesign.bucket;

import android.view.View;

import com.thea.fordesign.base.BasePresenter;
import com.thea.fordesign.bean.DribbbleBucket;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface SelectableBucketItemPresenter extends BasePresenter {

    void shotToBucket(View v, DribbbleBucket bucket, boolean add);

}
