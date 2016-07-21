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
import com.thea.fordesign.shot.shots.MyShotsActivity;
import com.thea.fordesign.shot.shots.ShotsFragment;
import com.thea.fordesign.shot.shots.ShotsPresenter;
import com.thea.fordesign.sign.SignInActivity;
import com.thea.fordesign.user.followers.FollowersFragment;
import com.thea.fordesign.user.followers.MyFollowersActivity;
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

    private DribbbleService mService;
    private UserModel userModel;

    private DrawerLayout mDrawerLayout;
    private CircleImageView mAvatar;
    private TextView mUsername;

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
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        View view = navigationView.getHeaderView(0);
        mAvatar = (CircleImageView) view.findViewById(R.id.iv_user_avatar);
        mUsername = (TextView) view.findViewById(R.id.tv_user_name);
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

    public void refreshUser() {
        if (userModel.getUserSignIn() && userModel.getDribbbleUserAccessToken() != null) {
            Call<DribbbleUser> call = mService.getUser(userModel.getDribbbleUserAccessToken());
            call.enqueue(new Callback<DribbbleUser>() {
                @Override
                public void onResponse(Call<DribbbleUser> call, Response<DribbbleUser> response) {
                    LogUtil.i(TAG, "get user code: " + response.code() + ", message: " +
                            response.message());
                    mUser = response.body();
                    mUsername.setText(mUser.getName());
                    Glide.with(MainActivity.this)
                            .load(mUser.getAvatarUrl())
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .centerCrop()
                            .placeholder(R.mipmap.ic_dribbble_square)
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
            Intent intent;
            switch (item.getItemId()) {
                case R.id.nav_my_shots:
                    intent = new Intent(this, MyShotsActivity.class);
                    if (mUser != null)
                        intent.putExtra(MyShotsActivity.EXTRA_SHOTS_URL, mUser.getShotsUrl());
                    startActivity(intent);
                    break;
                case R.id.nav_my_followers:
                    intent = new Intent(this, MyFollowersActivity.class);
                    if (mUser != null) {
                        intent.putExtra(MyFollowersActivity.EXTRA_FOLLOWER_URL, mUser.getFollowersUrl());
                        intent.putExtra(MyFollowersActivity.EXTRA_ITEM_TYPE, FollowersFragment
                                .TYPE_FOLLOWER);
                    }
                    startActivity(intent);
                    break;
                case R.id.nav_my_following:
                    intent = new Intent(this, MyFollowersActivity.class);
                    if (mUser != null) {
                        intent.putExtra(MyFollowersActivity.EXTRA_FOLLOWER_URL, mUser.getFollowingUrl());
                        intent.putExtra(MyFollowersActivity.EXTRA_ITEM_TYPE, FollowersFragment
                                .TYPE_FOLLOWING);
                    }
                    startActivity(intent);
                    break;
                default:
                    break;
            }
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
        }
        return true;
    }
}
