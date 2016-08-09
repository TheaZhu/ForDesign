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

    interface SaveBucketCallback {

        void onBucketSaved(DribbbleBucket bucket);

        void onFailed(int errCode, String message);
    }

    interface DeleteBucketCallback {

        void onBucketDeleted();

        void onFailed(int errCode, String message);
    }

    void getBuckets(@NonNull String authorization, @Nullable String url, int page,
                    LoadBucketsCallback callback);

    void getBucket(@NonNull String authorization, int bucketId, GetBucketCallback callback);

    void createBucket(@NonNull String authorization, @NonNull String name, String description,
                      SaveBucketCallback callback);

    void updateBucket(@NonNull String authorization, int bucketId, @NonNull String name, String
            description, SaveBucketCallback callback);

    void deleteBucket(@NonNull String authorization, int bucketId, DeleteBucketCallback callback);

    void refreshBuckets();

    void deleteAllBuckets();

    void deleteBucket(int bucketId);

    void updateBucket(DribbbleBucket bucket);
}
