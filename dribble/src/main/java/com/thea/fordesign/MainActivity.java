package com.thea.fordesign;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.thea.fordesign.base.BaseActivity;
import com.thea.fordesign.shot.shots.ShotsFragment;
import com.thea.fordesign.shot.shots.ShotsPresenter;
import com.thea.fordesign.util.ActivityUtil;

public class MainActivity extends BaseActivity implements NavigationView
        .OnNavigationItemSelectedListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawerLayout();

        ShotsFragment shotsFragment = (ShotsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fl_content);
        if (shotsFragment == null) {
            shotsFragment = ShotsFragment.newInstance();
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), shotsFragment, R.id.fl_content);
        }
        new ShotsPresenter(shotsFragment);
    }

    private void initDrawerLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_home);
        assert mDrawerLayout != null;
        mDrawerLayout.setStatusBarBackground(R.color.dribbble_pink);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    /*private void initTabLayoutWithViewPager() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_shots_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_shots);
        ShotsAdapter adapter = new ShotsAdapter(getSupportFragmentManager());
        ShotsFragment shotsFragment = ShotsFragment.newInstance();
        adapter.addItem(shotsFragment, "ANIMATED");
        adapter.addItem(ShotsFragment.newInstance(), "DEBUTS");
        adapter.addItem(ShotsFragment.newInstance(), "TEAMS");
        adapter.addItem(ShotsFragment.newInstance(), "REBOUNDS");

        if (viewPager != null) {
            viewPager.setAdapter(adapter);
        }
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
        shotsFragment.setUserVisibleHint(true);
    }*/

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
        switch (item.getItemId()) {
            default:
                break;
        }
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }


}
