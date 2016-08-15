package com.thea.fordesign.user.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.bucket.buckets.BucketsActivity;
import com.thea.fordesign.databinding.UserProfileActBinding;
import com.thea.fordesign.like.user.UserLikesActivity;
import com.thea.fordesign.project.projects.ProjectsActivity;
import com.thea.fordesign.setting.SettingsActivity;
import com.thea.fordesign.shot.shots.ShotsActivity;
import com.thea.fordesign.user.followers.FollowersActivity;
import com.thea.fordesign.user.followers.FollowersFragment;

public class UserProfileActivity extends BaseDataBindingActivity<UserProfileActBinding>
        implements UserProfileContract.View {
    public static final String TAG = UserProfileActivity.class.getSimpleName();
    public static final String EXTRA_USER = "dribbble_user";

    public static final int REQUEST_SETTINGS = 21;

    private DribbbleUser mUser;
    private UserProfileContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = getIntent().getParcelableExtra(EXTRA_USER);
        mPresenter = new UserProfilePresenter(this);

        mViewDataBinding.setUser(mUser);
        mViewDataBinding.setActionHandler(mPresenter);

        Glide.with(this)
                .load(mUser.getAvatarUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .placeholder(R.mipmap.default_user_avatar)
                .into(mViewDataBinding.ivAvatar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_profile;
    }

    @Override
    public void showShotsUi(@NonNull String shotsUrl) {
        Intent intent = new Intent(this, ShotsActivity.class);
        intent.putExtra(ShotsActivity.EXTRA_TITLE, getString(R.string.title_my_shots));
        intent.putExtra(ShotsActivity.EXTRA_SHOTS_URL, shotsUrl);
        startActivity(intent);
    }

    @Override
    public void showFollowingsUi(@NonNull String followingUrl) {
        Intent intent = new Intent(this, FollowersActivity.class);
        intent.putExtra(FollowersActivity.EXTRA_TITLE, getString(R.string.title_my_following));
        intent.putExtra(FollowersActivity.EXTRA_FOLLOWER_URL, followingUrl);
        intent.putExtra(FollowersActivity.EXTRA_ITEM_TYPE, FollowersFragment
                .TYPE_FOLLOWING);
        startActivity(intent);
    }

    @Override
    public void showLikesUi(@NonNull String likesUrl) {
        Intent intent = new Intent(this, UserLikesActivity.class);
        intent.putExtra(UserLikesActivity.EXTRA_TITLE, getString(R.string.title_my_likes));
        intent.putExtra(UserLikesActivity.EXTRA_LIKE_URL, likesUrl);
        startActivity(intent);
    }

    @Override
    public void showBucketsUi(@NonNull String bucketsUrl) {
        Intent intent = new Intent(this, BucketsActivity.class);
        intent.putExtra(BucketsActivity.EXTRA_TITLE, getString(R.string.title_my_buckets));
        intent.putExtra(BucketsActivity.EXTRA_BUCKETS_URL, bucketsUrl);
        intent.putExtra(BucketsActivity.EXTRA_CAN_ADD, true);
        startActivity(intent);
    }

    @Override
    public void showProjectsUi(int userId) {
        Intent intent = new Intent(this, ProjectsActivity.class);
        intent.putExtra(ProjectsActivity.EXTRA_TITLE, getString(R.string.title_my_projects));
        intent.putExtra(ProjectsActivity.EXTRA_USER_ID, userId);
        startActivity(intent);
    }

    @Override
    public void showFollowersUi(@NonNull String followersUrl) {
        Intent intent = new Intent(this, FollowersActivity.class);
        intent.putExtra(FollowersActivity.EXTRA_TITLE, getString(R.string.title_my_followers));
        intent.putExtra(FollowersActivity.EXTRA_FOLLOWER_URL, followersUrl);
        intent.putExtra(FollowersActivity.EXTRA_ITEM_TYPE, FollowersFragment
                .TYPE_FOLLOWER);
        startActivity(intent);
    }

    @Override
    public void showPrevious() {
        finish();
    }

    @Override
    public void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, REQUEST_SETTINGS);
    }

    @Override
    public void setPresenter(@NonNull UserProfileContract.Presenter presenter) {
//        mPresenter = Preconditions.checkNotNull(presenter, "presenter cannot be null");
    }
}
