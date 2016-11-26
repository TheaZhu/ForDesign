package com.thea.fordesign.bucket.selectable;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.bean.DribbbleBucket;
import com.thea.fordesign.bucket.data.BucketsDataSource;
import com.thea.fordesign.bucket.data.BucketsRepository;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.util.List;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class ShotToBucketsPresenter implements ShotToBucketsContract.Presenter {
    public static final String TAG = ShotToBucketsPresenter.class.getSimpleName();

    private final ShotToBucketsContract.View mView;
    private final UserModel mUserModel;
    private BucketsRepository mRepository;

    private int mShotId;

    public ShotToBucketsPresenter(@NonNull ShotToBucketsContract.View view, int shotId,
                                  @NonNull UserModel userModel) {
        mView = Preconditions.checkNotNull(view, "view cannot be null");
        mUserModel = Preconditions.checkNotNull(userModel, "userModel cannot be null");
        mShotId = shotId;
        mRepository = BucketsRepository.getInstance();

        mView.setPresenter(this);
    }

    @Override
    public void loadBuckets() {
        mView.setRefreshingIndicator(true);

        mRepository.refreshBuckets();

        mRepository.getBuckets(mUserModel.getDribbbleAccessToken(), 1, new
                BucketsDataSource.LoadBucketsCallback() {

                    @Override
                    public void onBucketsLoaded(List<DribbbleBucket> buckets) {
                        mView.setRefreshingIndicator(false);
                        mView.showBuckets(buckets);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mView.setRefreshingIndicator(false);
                        mView.showSnack(R.string.error_load_buckets);
                    }
                });

    }

    @Override
    public void loadMore(int page) {
        mView.setLoadingIndicator(true, true, R.string.loading, false);

        mRepository.refreshBuckets();

        mRepository.getBuckets(mUserModel.getDribbbleAccessToken(), page, new
                BucketsDataSource.LoadBucketsCallback() {

                    @Override
                    public void onBucketsLoaded(List<DribbbleBucket> buckets) {
                        mView.setLoadingIndicator(false, false, R.string.loading, false);
                        mView.insertBuckets(buckets);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mView.setLoadingIndicator(true, false,
                                R.string.loading_error, true);
                        mView.setLoadingError();
                    }
                });
    }

    @Override
    public void createNewBucket(String name, String description) {
        if (TextUtils.isEmpty(name))
            mView.showSnack(R.string.msg_empty_bucket_name);
        else
            mRepository.createBucket(mUserModel.getDribbbleUserAccessToken(), name, description,
                    new BucketsDataSource.SaveBucketCallback() {
                        @Override
                        public void onBucketSaved(DribbbleBucket bucket) {
                            mView.insertBucket(bucket);
                            mView.showSnack(R.string.msg_create_bucket_success);
                        }

                        @Override
                        public void onFailed(int errCode, String message) {
                            mView.showSnack(R.string.msg_create_bucket_fail);
                        }
                    });
    }

    @Override
    public void shotToBucket(View v, DribbbleBucket bucket, boolean add) {
        LogUtil.i(TAG, add ? "add shot " + mShotId + " to bucket" : "delete shot " + mShotId + " " +
                "from bucket");
        if (add)
            addShotToBucket(v, bucket);
        else
            removeShotFromBucket(v, bucket);
    }

    private void addShotToBucket(final View v, final DribbbleBucket bucket) {
        mRepository.addShotToBucket(mUserModel.getDribbbleUserAccessToken(), bucket.getId(),
                mShotId, new BucketsDataSource.AddShotToBucketCallback() {

                    @Override
                    public void onSuccess() {
                        bucket.increaseShotsCount();
                        mView.showItemSelected(v, true);
                    }

                    @Override
                    public void onFail(int errCode, String message) {
                        mView.showSnack(R.string.msg_add_shot_to_bucket_fail);
                    }
                });
    }

    private void removeShotFromBucket(final View v, final DribbbleBucket bucket) {
        mRepository.removeShotFromBucket(mUserModel.getDribbbleUserAccessToken(), bucket.getId(),
                mShotId, new BucketsDataSource.RemoveShotFromBucketCallback() {

                    @Override
                    public void onSuccess() {
                        bucket.decreaseShotsCount();
                        mView.showItemSelected(v, false);
                    }

                    @Override
                    public void onFail(int errCode, String message) {
                        mView.showSnack(R.string.msg_remove_shot_from_bucket_fail);
                    }
                });
    }

    @Override
    public void start() {

    }
}
