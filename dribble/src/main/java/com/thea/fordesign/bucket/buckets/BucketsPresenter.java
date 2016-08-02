package com.thea.fordesign.bucket.buckets;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.bucket.data.BucketsDataSource;
import com.thea.fordesign.bucket.data.BucketsRepository;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class BucketsPresenter implements BucketsContract.Presenter {
    public static final String TAG = BucketsPresenter.class.getSimpleName();

    private final BucketsContract.View mBucketsView;
    private final UserModel mUserModel;

    private BucketsRepository mRepository;

    private String mBucketsUrl;

    public BucketsPresenter(@NonNull String bucketsUrl, @NonNull BucketsContract.View bucketsView,
                            @NonNull UserModel userModel) {
        mBucketsUrl = bucketsUrl;
        mBucketsView = Preconditions.checkNotNull(bucketsView, "shotsView cannot be null");
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");

        mRepository = BucketsRepository.getInstance();

        mBucketsView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void loadBuckets() {
        loadBuckets(mBucketsUrl, 1, true, false);
    }

    @Override
    public void loadMore(int page) {
        loadBuckets(mBucketsUrl, page, false, true);
    }

    @Override
    public void createNewBucket(String name, String description) {
        if (TextUtils.isEmpty(name))
            mBucketsView.showSnack(R.string.msg_empty_bucket_name);
        else
            mRepository.createBucket(mUserModel.getDribbbleUserAccessToken(), name, description,
                    new BucketsDataSource.SaveBucketCallback() {
                        @Override
                        public void onBucketSaved(DribbbleBucket bucket) {
                            mBucketsView.insertBucket(bucket);
                            mBucketsView.showSnack(R.string.msg_create_bucket_success);
                        }

                        @Override
                        public void onFailed(int errCode, String message) {
                            mBucketsView.showSnack(R.string.msg_create_bucket_fail);
                        }
                    });
    }

    @Override
    public void updateBucket(int bucketId, String name, String description) {
        if (!TextUtils.isEmpty(name))
            mRepository.updateBucket(mUserModel.getDribbbleUserAccessToken(), bucketId, name,
                    description, new BucketsDataSource.SaveBucketCallback() {
                        @Override
                        public void onBucketSaved(DribbbleBucket bucket) {
                            mBucketsView.showSnack(R.string.msg_update_bucket_success);
                        }

                        @Override
                        public void onFailed(int errCode, String message) {
                            mBucketsView.showSnack(R.string.msg_update_bucket_fail);
                        }
                    });
    }

    @Override
    public void deleteBucket(int bucketId) {
        mRepository.deleteBucket(mUserModel.getDribbbleUserAccessToken(), bucketId, new
                BucketsDataSource.DeleteBucketCallback() {

                    @Override
                    public void onBucketDeleted() {
                        mBucketsView.showSnack(R.string.msg_delete_bucket_success);
                    }

                    @Override
                    public void onFailed(int errCode, String message) {
                        mBucketsView.showSnack(R.string.msg_delete_bucket_fail);
                    }
                });
    }

    private void loadBuckets(String url, final int page, final boolean showLoadingUI, final boolean
            isLoadMore) {
        if (showLoadingUI)
            mBucketsView.setRefreshingIndicator(true);

        if (isLoadMore)
            mBucketsView.setLoadingIndicator(true, true, R.string.loading, false);

        mRepository.refreshBuckets();

        mRepository.getBuckets(mUserModel.getDribbbleAccessToken(), url, page, new
                BucketsDataSource.LoadBucketsCallback() {

                    @Override
                    public void onBucketsLoaded(List<DribbbleBucket> buckets) {
                        if (showLoadingUI)
                            mBucketsView.setRefreshingIndicator(false);
                        if (isLoadMore)
                            mBucketsView.setLoadingIndicator(false, false, R.string.loading, false);
                        if (page == 1)
                            mBucketsView.showBuckets(buckets);
                        else
                            mBucketsView.insertBuckets(buckets);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (showLoadingUI) {
                            mBucketsView.setRefreshingIndicator(false);
                            mBucketsView.showSnack(R.string.error_load_buckets);
                        }
                        if (isLoadMore) {
                            mBucketsView.setLoadingIndicator(true, false,
                                    R.string.loading_error, true);
                            mBucketsView.setLoadingError();
                        }
                    }
                });
    }

    @Override
    public String formatTime(String timeStr) {
        return timeStr.substring(0, timeStr.indexOf("T"));
    }

    @Override
    public void openBucketShots(int bucketId) {
        mBucketsView.showBucketShotsUi(bucketId);
    }

    @Override
    public void editBucket(DribbbleBucket bucket) {
        mBucketsView.showBucketEditDialog(bucket.getId(), bucket.getName(), bucket.getDescription
                ());
    }

    @Override
    public void deleteBucket(DribbbleBucket bucket) {
        mBucketsView.showBucketDeleteDialog(bucket.getId());
    }

    @Override
    public void start() {
    }
}
