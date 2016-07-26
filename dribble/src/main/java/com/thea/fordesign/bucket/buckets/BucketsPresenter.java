package com.thea.fordesign.bucket.buckets;

import android.support.annotation.NonNull;

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
        loadBuckets(mBucketsUrl, 1, true);
    }

    @Override
    public void loadMore(int page) {
        loadBuckets(mBucketsUrl, page, false);
    }

    private void loadBuckets(String url, final int page, final boolean showLoadingUI) {
        if (showLoadingUI)
            mBucketsView.setLoadingIndicator(true);

        mRepository.refreshBuckets();

        mRepository.getBuckets(mUserModel.getDribbbleAccessToken(), url, page, new
                BucketsDataSource.LoadBucketsCallback() {

            @Override
            public void onBucketsLoaded(List<DribbbleBucket> buckets) {
                if (showLoadingUI)
                    mBucketsView.setLoadingIndicator(false);
                if (page == 1)
                    mBucketsView.showBuckets(buckets);
                else
                    mBucketsView.insertBuckets(buckets);
            }

            @Override
            public void onDataNotAvailable() {
                if (showLoadingUI)
                    mBucketsView.setLoadingIndicator(false);
                mBucketsView.showSnack(R.string.error_load_buckets);
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
    public void start() {
    }
}
