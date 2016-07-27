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
        loadBuckets(mBucketsUrl, 1, true, false);
    }

    @Override
    public void loadMore(int page) {
        loadBuckets(mBucketsUrl, page, false, true);
    }

    private void loadBuckets(String url, final int page, final boolean showLoadingUI, final boolean
            isLoadMore) {
        if (showLoadingUI)
            mBucketsView.setRefreshingIndicator(true);

        if (isLoadMore)
            mBucketsView.setLoadingIndicator(true, R.string.loading, false);

        mRepository.refreshBuckets();

        mRepository.getBuckets(mUserModel.getDribbbleAccessToken(), url, page, new
                BucketsDataSource.LoadBucketsCallback() {

            @Override
            public void onBucketsLoaded(List<DribbbleBucket> buckets) {
                if (showLoadingUI)
                    mBucketsView.setRefreshingIndicator(false);
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
                    mBucketsView.setLoadingIndicator(false, R.string.loading_error, true);
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
    public void start() {
    }
}
