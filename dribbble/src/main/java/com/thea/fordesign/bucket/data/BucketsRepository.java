package com.thea.fordesign.bucket.data;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import com.thea.fordesign.DribbbleService;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.config.DribbbleConstant;
import com.thea.fordesign.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class BucketsRepository implements BucketsDataSource {
    public static final String TAG = BucketsRepository.class.getSimpleName();

    private DribbbleService mService;

    Map<Integer, DribbbleBucket> mCachedBuckets;
    boolean mCacheIsDirty = false;

    private BucketsRepository() {
        mService = new DribbbleService.Builder().create();
    }

    public static BucketsRepository getInstance() {
        return Singleton.INSTANCE;
    }


    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getBuckets(@NonNull String authorization, @Nullable String url, final int page,
                           final LoadBucketsCallback callback) {
        if (mCachedBuckets == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get buckets");
            Call<List<DribbbleBucket>> call = mService.getBuckets(authorization, url, page);
            call.enqueue(new Callback<List<DribbbleBucket>>() {
                @Override
                public void onResponse(Call<List<DribbbleBucket>> call,
                                       Response<List<DribbbleBucket>> response) {
                    LogUtil.i(TAG, "get buckets code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleBucket> buckets = response.body();
                    refreshCache(page, buckets);
                    if (callback != null)
                        callback.onBucketsLoaded(buckets);
                }

                @Override
                public void onFailure(Call<List<DribbbleBucket>> call, Throwable t) {
                    LogUtil.i(TAG, "get buckets call executed: " + call.isExecuted() + ", url: " +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onBucketsLoaded(new ArrayList<>(mCachedBuckets.values()));
        }
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getBuckets(@NonNull String authorization, final int page, final
    LoadBucketsCallback callback) {
        if (mCachedBuckets == null || mCacheIsDirty) {
            LogUtil.i(TAG, "get buckets");
            Call<List<DribbbleBucket>> call = mService.getUserBuckets(authorization, page);
            call.enqueue(new Callback<List<DribbbleBucket>>() {
                @Override
                public void onResponse(Call<List<DribbbleBucket>> call,
                                       Response<List<DribbbleBucket>> response) {
                    LogUtil.i(TAG, "get buckets code: " + response.code() + ", message: " +
                            response.message());
                    List<DribbbleBucket> buckets = response.body();
                    refreshCache(page, buckets);
                    if (callback != null)
                        callback.onBucketsLoaded(buckets);
                }

                @Override
                public void onFailure(Call<List<DribbbleBucket>> call, Throwable t) {
                    LogUtil.i(TAG, "get buckets call executed: " + call.isExecuted() + ", url: " +
                            call.request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        } else if (callback != null) {
            callback.onBucketsLoaded(new ArrayList<>(mCachedBuckets.values()));
        }
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void getBucket(@NonNull String authorization, int bucketId, final GetBucketCallback
            callback) {
        DribbbleBucket cachedBucket = getBucketWithId(bucketId);

        // Respond immediately with cache if available
        if (cachedBucket != null) {
            LogUtil.i(TAG, "get bucket local: " + bucketId);
            if (callback != null)
                callback.onBucketLoaded(cachedBucket);
        } else {
            LogUtil.i(TAG, "get bucket: " + bucketId);
            Call<DribbbleBucket> call = mService.getBucket(authorization, bucketId);
            call.enqueue(new Callback<DribbbleBucket>() {
                @Override
                public void onResponse(Call<DribbbleBucket> call, Response<DribbbleBucket>
                        response) {
                    LogUtil.i(TAG, "get bucket code: " + response.code() + ", message: " + response
                            .message());
                    if (callback != null)
                        callback.onBucketLoaded(response.body());
                }

                @Override
                public void onFailure(Call<DribbbleBucket> call, Throwable t) {
                    LogUtil.i(TAG, "get bucket call executed: " + call.isExecuted() + ", url: " +
                            call
                                    .request().url());
                    t.printStackTrace();
                    if (callback != null)
                        callback.onDataNotAvailable();
                }
            });
        }

    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void createBucket(@NonNull String authorization, @NonNull String name, String
            description, final SaveBucketCallback callback) {
        Call<DribbbleBucket> call = mService.createBucket(authorization, name, description);
        call.enqueue(new Callback<DribbbleBucket>() {
            @Override
            public void onResponse(Call<DribbbleBucket> call, Response<DribbbleBucket>
                    response) {
                LogUtil.i(TAG, "create bucket code: " + response.code() + ", message: " + response
                        .message());
                if (callback != null) {
                    int code = response.code();
                    if (code == DribbbleConstant.CODE_CREATED)
                        callback.onBucketSaved(response.body());
                    else
                        callback.onFailed(code, response.message());
                }
            }

            @Override
            public void onFailure(Call<DribbbleBucket> call, Throwable t) {
                LogUtil.i(TAG, "create bucket call executed: " + call.isExecuted() + ", url: " +
                        call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFailed(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void updateBucket(@NonNull String authorization, int bucketId, @NonNull String name,
                             String description, final SaveBucketCallback callback) {
        Call<DribbbleBucket> call = mService.updateBucket(authorization, bucketId, name,
                description);
        call.enqueue(new Callback<DribbbleBucket>() {
            @Override
            public void onResponse(Call<DribbbleBucket> call, Response<DribbbleBucket>
                    response) {
                LogUtil.i(TAG, "update bucket code: " + response.code() + ", message: " + response
                        .message());
                if (callback != null) {
                    int code = response.code();
                    if (code == DribbbleConstant.CODE_OK)
                        callback.onBucketSaved(response.body());
                    else
                        callback.onFailed(code, response.message());
                }
            }

            @Override
            public void onFailure(Call<DribbbleBucket> call, Throwable t) {
                LogUtil.i(TAG, "update bucket call executed: " + call.isExecuted() + ", url: " +
                        call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFailed(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void deleteBucket(@NonNull String authorization, int bucketId, final
    DeleteBucketCallback callback) {
        Call<Void> call = mService.deleteBucket(authorization, bucketId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                String message = response.message();
                LogUtil.i(TAG, "delete bucket code: " + code + ", message: " + message);

                if (callback == null)
                    return;
                if (code == DribbbleConstant.CODE_NO_CONTENT)
                    callback.onBucketDeleted();
                else
                    callback.onFailed(code, message);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LogUtil.i(TAG, "delete bucket call executed: " + call.isExecuted() +
                        ", url: " + call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFailed(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void addShotToBucket(@NonNull String authorization, int bucketId, int shotId, final
    AddShotToBucketCallback callback) {
        Call<Void> call = mService.addShotToBucket(authorization, bucketId, shotId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                String message = response.message();
                LogUtil.i(TAG, "add shot to bucket code: " + code + ", message: " + message);

                if (callback == null)
                    return;
                if (code == DribbbleConstant.CODE_NO_CONTENT)
                    callback.onSuccess();
                else
                    callback.onFail(code, message);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LogUtil.i(TAG, "add shot to bucket call executed: " + call.isExecuted() +
                        ", url: " + call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFail(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Override
    public void removeShotFromBucket(@NonNull String authorization, int bucketId, int shotId,
                                     final RemoveShotFromBucketCallback callback) {
        Call<Void> call = mService.removeShotFromBucket(authorization, bucketId, shotId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                String message = response.message();
                LogUtil.i(TAG, "remove shot from bucket code: " + code + ", message: " + message);

                if (callback == null)
                    return;
                if (code == DribbbleConstant.CODE_NO_CONTENT)
                    callback.onSuccess();
                else
                    callback.onFail(code, message);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                LogUtil.i(TAG, "remove shot from bucket call executed: " + call.isExecuted() +
                        ", url: " + call.request().url());
                t.printStackTrace();
                if (callback != null)
                    callback.onFail(DribbbleConstant.CODE_REQUEST_FAIL, t.getMessage());
            }
        });
    }

    @Override
    public void refreshBuckets() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllBuckets() {
        if (mCachedBuckets == null) {
            mCachedBuckets = new LinkedHashMap<>();
        }
        mCachedBuckets.clear();
    }

    @Override
    public void deleteBucket(int bucketId) {
        mCachedBuckets.remove(bucketId);
    }

    @Override
    public void updateBucket(DribbbleBucket bucket) {
        mCachedBuckets.put(bucket.getId(), bucket);
    }

    private void refreshCache(int page, List<DribbbleBucket> buckets) {
        if (mCachedBuckets == null) {
            mCachedBuckets = new LinkedHashMap<>();
        }
        if (page <= 1)
            mCachedBuckets.clear();
        for (DribbbleBucket bucket : buckets) {
            mCachedBuckets.put(bucket.getId(), bucket);
        }
        mCacheIsDirty = false;
    }

    @Nullable
    private DribbbleBucket getBucketWithId(int id) {
        if (mCachedBuckets == null || mCachedBuckets.isEmpty()) {
            return null;
        } else {
            return mCachedBuckets.get(id);
        }
    }

    private static class Singleton {
        private static final BucketsRepository INSTANCE = new BucketsRepository();
    }
}
