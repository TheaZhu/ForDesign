package com.thea.fordesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thea.fordesign.base.BaseActivity;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.bucket.buckets.BucketsActivity;
import com.thea.fordesign.like.user.UserLikesActivity;
import com.thea.fordesign.project.projects.ProjectsActivity;
import com.thea.fordesign.setting.SettingsActivity;
import com.thea.fordesign.shot.shots.ShotsActivity;
import com.thea.fordesign.shot.shots.ShotsFragment;
import com.thea.fordesign.shot.shots.ShotsPresenter;
import com.thea.fordesign.sign.SignInActivity;
import com.thea.fordesign.user.followers.FollowersActivity;
import com.thea.fordesign.user.followers.FollowersFragment;
import com.thea.fordesign.util.ActivityUtil;
import com.thea.fordesign.util.LogUtil;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements NavigationView
        .OnNavigationItemSelectedListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_SIGN_IN = 20;
    public static final int REQUEST_SETTINGS = 21;
    public static final int RESULT_SIGN_OUT = 22;

    private DribbbleService mService;
    private UserModel userModel;

    private DrawerLayout mDrawerLayout;
    private CircleImageView mAvatar;
    private TextView mUsername;
    private TextView mBio;

    private DribbbleUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = new DribbbleService.Builder().create();
        userModel = new UserModel(this);

        initDrawerLayout();

        ShotsFragment shotsFragment = (ShotsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fl_content);
        if (shotsFragment == null) {
            shotsFragment = ShotsFragment.newInstance();
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), shotsFragment, R.id.fl_content);
        }
        new ShotsPresenter(shotsFragment, new UserModel(this));
    }

    private void initDrawerLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_home);
        assert mDrawerLayout != null;
//        mDrawerLayout.setStatusBarBackground(R.color.dribbble_pink);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        View view = navigationView.getHeaderView(0);
        mAvatar = (CircleImageView) view.findViewById(R.id.iv_user_avatar);
        mUsername = (TextView) view.findViewById(R.id.tv_user_name);
        mBio = (TextView) view.findViewById(R.id.tv_user_bio);
        view.setOnClickListener(mHeaderClickListener);
        refreshUser();
    }

    private View.OnClickListener mHeaderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!userModel.getUserSignIn()) {
                trySignIn();
            }
        }
    };

    private void refreshUser() {
        if (userModel.getUserSignIn() && userModel.getDribbbleUserAccessToken() != null) {
            Call<DribbbleUser> call = mService.getUser(userModel.getDribbbleUserAccessToken());
            call.enqueue(new Callback<DribbbleUser>() {
                @Override
                public void onResponse(Call<DribbbleUser> call, Response<DribbbleUser> response) {
                    LogUtil.i(TAG, "get user code: " + response.code() + ", message: " +
                            response.message());
                    mUser = response.body();
                    userModel.setDribbbleUser(mUser);
                    mUsername.setText(mUser.getName());
                    mBio.setText(mUser.getBio());
                    Glide.with(MainActivity.this)
                            .load(mUser.getAvatarUrl())
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .centerCrop()
                            .placeholder(R.mipmap.default_user_avatar)
                            .into(mAvatar);
                }

                @Override
                public void onFailure(Call<DribbbleUser> call, Throwable t) {
                    LogUtil.i(TAG, "get user call executed: " + call.isExecuted() + ", url: " +
                            call.request().url());
                }
            });
        } else {
            mAvatar.setImageResource(R.mipmap.ic_dribbble_square);
            mUsername.setText(R.string.sign_in);
        }
    }

    public void trySignIn() {
        startActivityForResult(new Intent(MainActivity.this, SignInActivity.class),
                REQUEST_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGN_IN)
            refreshUser();
        else if (requestCode == REQUEST_SETTINGS && resultCode == RESULT_SIGN_OUT)
            refreshUser();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (!userModel.getUserSignIn() || userModel.getDribbbleUserAccessToken() == null) {
            trySignIn();
        } else {
            switch (item.getItemId()) {
                case R.id.nav_my_shots:
                    showMyShots();
                    break;
                case R.id.nav_my_projects:
                    showMyProjects();
                    break;
                case R.id.nav_my_buckets:
                    showMyBuckets();
                    break;
                case R.id.nav_my_likes:
                    showMyLikes();
                    break;
                case R.id.nav_my_followers:
                    showMyFollowers();
                    break;
                case R.id.nav_my_following:
                    showMyFollowings();
                    break;
                case R.id.nav_settings:
                    showSettings();
                    break;
                default:
                    mDrawerLayout.closeDrawers();
                    break;
            }
            item.setChecked(true);
        }
        return true;
    }

    private void showMyShots() {
        Intent intent = new Intent(this, ShotsActivity.class);
        if (mUser != null) {
            intent.putExtra(ShotsActivity.EXTRA_TITLE, getString(R.string.title_my_shots));
            intent.putExtra(ShotsActivity.EXTRA_SHOTS_URL, mUser.getShotsUrl());
        }
        startActivity(intent);
    }

    private void showMyProjects() {
        Intent intent = new Intent(this, ProjectsActivity.class);
        if (mUser != null) {
            intent.putExtra(ProjectsActivity.EXTRA_TITLE, getString(R.string.title_my_projects));
            intent.putExtra(ProjectsActivity.EXTRA_USER_ID, mUser.getId());
        }
        startActivity(intent);
    }

    private void showMyBuckets() {
        Intent intent = new Intent(this, BucketsActivity.class);
        if (mUser != null) {
            intent.putExtra(BucketsActivity.EXTRA_TITLE, getString(R.string.title_my_buckets));
            intent.putExtra(BucketsActivity.EXTRA_BUCKETS_URL, mUser.getBucketsUrl());
            intent.putExtra(BucketsActivity.EXTRA_CAN_ADD, true);
        }
        startActivity(intent);
    }

    private void showMyLikes() {
        Intent intent = new Intent(this, UserLikesActivity.class);
        if (mUser != null) {
            intent.putExtra(UserLikesActivity.EXTRA_TITLE, getString(R.string.title_my_likes));
            intent.putExtra(UserLikesActivity.EXTRA_LIKE_URL, mUser.getLikesUrl());
        }
        startActivity(intent);
    }

    private void showMyFollowers() {
        Intent intent = new Intent(this, FollowersActivity.class);
        if (mUser != null) {
            intent.putExtra(FollowersActivity.EXTRA_TITLE, getString(R.string.title_my_followers));
            intent.putExtra(FollowersActivity.EXTRA_FOLLOWER_URL, mUser.getFollowersUrl());
            intent.putExtra(FollowersActivity.EXTRA_ITEM_TYPE, FollowersFragment
                    .TYPE_FOLLOWER);
        }
        startActivity(intent);
    }

    private void showMyFollowings() {
        Intent intent = new Intent(this, FollowersActivity.class);
        if (mUser != null) {
            intent.putExtra(FollowersActivity.EXTRA_TITLE, getString(R.string.title_my_following));
            intent.putExtra(FollowersActivity.EXTRA_FOLLOWER_URL, mUser
                    .getFollowingUrl());
            intent.putExtra(FollowersActivity.EXTRA_ITEM_TYPE, FollowersFragment
                    .TYPE_FOLLOWING);
        }
        startActivity(intent);
    }

    private void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, REQUEST_SETTINGS);
    }
}
