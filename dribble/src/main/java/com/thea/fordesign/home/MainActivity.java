package com.thea.fordesign.home;

import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fg_home);
        new HomePresenter(homeFragment);
    }
}
