package com.thea.fordesign.user.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.UserDetailActBinding;
import com.thea.fordesign.shot.shots.ShotsFragment;
import com.thea.fordesign.shot.shots.ShotsPresenter;
import com.thea.fordesign.team.detail.TeamDetailFragment;
import com.thea.fordesign.team.detail.TeamDetailPresenter;
import com.thea.fordesign.user.followers.FollowersFragment;
import com.thea.fordesign.user.followers.FollowersPresenter;
import com.thea.fordesign.util.ActivityUtil;
import com.thea.fordesign.util.LogUtil;
import com.thea.fordesign.util.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends BaseDataBindingActivity<UserDetailActBinding> implements
        UserDetailContract.View {
    public static final String TAG = UserDetailActivity.class.getSimpleName();

    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USER = "dribbble_user";

    private DribbbleUser mUser;

    private UserDetailContract.Presenter mPresenter;

    private MenuItem mFollowMenuItem;
    private SpannableString followString;
    private SpannableString followingString;
    private boolean isFollowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_USER)) {
            mUser = intent.getParcelableExtra(EXTRA_USER);
        }
        LogUtil.i(TAG, mUser.toString());
        new UserDetailPresenter(this, mUser, new UserModel(this));
        initTabLayoutWithViewPager();
        showUser(mUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_detail, menu);
        mFollowMenuItem = menu.findItem(R.id.action_follow);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_follow:
                if (isFollowing)
                    mPresenter.unFollowUser();
                else
                    mPresenter.followUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_detail;
    }

    private void initTabLayoutWithViewPager() {
        UserPagerAdapter adapter = new UserPagerAdapter(getSupportFragmentManager());
        if (mUser.isTeam(mUser.getType())) {
            TeamDetailFragment teamDetailFragment = TeamDetailFragment.newInstance();
            new TeamDetailPresenter(teamDetailFragment, mPresenter, mUser);
            adapter.addItem(teamDetailFragment, "DETAILS");

        } else {
            UserDetailFragment userDetailFragment = UserDetailFragment.newInstance(mUser);
            new UserDetailFragPresenter(userDetailFragment, mPresenter);
            adapter.addItem(userDetailFragment, "DETAILS");
        }

        ShotsFragment shotsFragment = ShotsFragment.newInstance(mUser.getShotsUrl(), false);
        new ShotsPresenter(shotsFragment, new UserModel(this));
        adapter.addItem(shotsFragment, "SHOTS");

        FollowersFragment followingsFragment = FollowersFragment.newInstance(mUser
                .getFollowingUrl(), FollowersFragment.TYPE_FOLLOWING, false);
        new FollowersPresenter(followingsFragment, new UserModel(this));
        adapter.addItem(followingsFragment, "FOLLOWINGS");

        /*ShotsFragment likesFragment = ShotsFragment.newInstance(mUser.getLikesUrl(), false);
        new ShotsPresenter(likesFragment);
        adapter.addItem(likesFragment, "LIKES");*/

        TabLayout tabLayout = mViewDataBinding.tlUserTabs;
        ViewPager viewPager = mViewDataBinding.vpUserPage;
        if (viewPager != null) {
            viewPager.setAdapter(adapter);
        }
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void showUser(DribbbleUser user) {
        mViewDataBinding.setUser(user);
        mViewDataBinding.setActionHandler(mPresenter);

        Glide.with(this)
                .load(user.getAvatarUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .placeholder(R.mipmap.default_user_avatar)
                .into(mViewDataBinding.ivAvatar);

        mViewDataBinding.executePendingBindings();
    }

    @Override
    public void showFollowed() {
        if (mFollowMenuItem == null)
            return;
        LogUtil.i(TAG, "followed");
        if (followingString == null) {
            followingString = new SpannableString(getString(R.string.action_following));
            followingString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, followingString
                    .length()
                    , 0);
        }
        mFollowMenuItem.setTitle(followingString);
        isFollowing = true;
    }

    @Override
    public void showUnfollowed() {
        if (mFollowMenuItem == null)
            return;
        LogUtil.i(TAG, "unfollowed");
        if (followString == null) {
            followString = new SpannableString(getString(R.string.action_follow));
            followString.setSpan(new ForegroundColorSpan(Color.RED), 0, followString.length(), 0);
        }
        mFollowMenuItem.setTitle(followString);
        isFollowing = false;
    }

    @Override
    public void showUserDetailsUi() {
        mViewDataBinding.vpUserPage.setCurrentItem(0, true);
    }

    @Override
    public void showShotsUi() {
        mViewDataBinding.vpUserPage.setCurrentItem(1, true);
    }

    @Override
    public void showFollowingsUi() {
        mViewDataBinding.vpUserPage.setCurrentItem(2, true);
    }

    @Override
    public void showLikesUi() {
        mViewDataBinding.vpUserPage.setCurrentItem(3, true);
    }

    @Override
    public void setPresenter(@NonNull UserDetailContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter, "presenter cannot be null");
    }

    @Override
    public void showSnack(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(mViewDataBinding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showSnack(@StringRes int resId) {
        showSnack(getString(resId));
    }

    public static class UserPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mItems = new ArrayList<>();
        private List<String> mTitles = new ArrayList<>();

        public UserPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment fragment, String title) {
            mItems.add(fragment);
            mTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
