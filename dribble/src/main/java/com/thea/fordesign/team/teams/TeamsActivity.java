package com.thea.fordesign.team.teams;

import android.content.Intent;
import android.os.Bundle;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.TeamsActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class TeamsActivity extends BaseDataBindingActivity<TeamsActBinding> {
    public static final String EXTRA_TEAMS_URL = "teams_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_TEAMS_URL)) {
            String mTeamUrl = intent.getStringExtra(EXTRA_TEAMS_URL);
            TeamsFragment teamsFragment = (TeamsFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fl_content);
            if (teamsFragment == null) {
                teamsFragment = TeamsFragment.newInstance();
                ActivityUtil.addFragmentToActivity(
                        getSupportFragmentManager(), teamsFragment, R.id.fl_content);
            }
            new TeamsPresenter(teamsFragment, mTeamUrl, new UserModel(this));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teams;
    }
}
