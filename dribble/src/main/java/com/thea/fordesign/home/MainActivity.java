package com.thea.fordesign.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseActivity;
import com.thea.fordesign.shot.shots.ShotsFragment;
import com.thea.fordesign.shot.shots.ShotsPresenter;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_home);
        assert mDrawerLayout != null;
        mDrawerLayout.setStatusBarBackground(R.color.pink_600);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        /*HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fg_home);
        new HomePresenter(homeFragment);*/

        ShotsFragment shotsFragment = (ShotsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fg_shots);
        new ShotsPresenter(shotsFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    /*public void test(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DribbleConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<DribbbleShot> call = retrofit.create(DribbbleService.class).getShot(DribbleConstant
        .AUTH_TYPE + DribbleConstant
                .CLIENT_ACCESS_TOKEN, 2222);
        call.enqueue(new Callback<DribbbleShot>() {
            @Override
            public void onResponse(Call<DribbbleShot> call, Response<DribbbleShot> response) {
                LogUtil.i(TAG, "code: " + response.code() + ", message: " + response.message());
            }

            @Override
            public void onFailure(Call<DribbbleShot> call, Throwable t) {
                LogUtil.i(TAG, "call executed: " + call.isExecuted() + ", url: " + call.request()
                .url());
//                LogUtil.i(TAG, "fail message: " + t.getMessage());
//                t.printStackTrace();
            }
        });
    }*/
}
