package com.thea.fordesign.bucket.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thea.fordesign.bean.DribbbleBucket;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public interface BucketsDataSource {

    interface LoadBucketsCallback {

        void onBucketsLoaded(List<DribbbleBucket> buckets);

        void onDataNotAvailable();
    }

    interface GetBucketCallback {

        void onBucketLoaded(DribbbleBucket bucket);

        void onDataNotAvailable();
    }

    void getBuckets(@NonNull String authorization, @Nullable String url, int page,
                  LoadBucketsCallback callback);

    void getBucket(@NonNull String authorization, int bucketId, GetBucketCallback callback);

    void saveBucket(@NonNull String authorization, @NonNull DribbbleBucket bucket);

    void refreshBuckets();

    void deleteAllBuckets();

    void deleteBucket(int bucketId);

}
