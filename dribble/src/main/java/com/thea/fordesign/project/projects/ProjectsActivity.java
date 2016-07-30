package com.thea.fordesign.project.projects;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.ProjectsActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class ProjectsActivity extends BaseDataBindingActivity<ProjectsActBinding> {
    public static final String EXTRA_TITLE = "activity_title";
    public static final String EXTRA_USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra(EXTRA_TITLE));

        int mUserId = intent.getIntExtra(EXTRA_USER_ID, -1);
        ProjectsFragment projectsFragment = (ProjectsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fl_content);
        if (projectsFragment == null) {
            projectsFragment = ProjectsFragment.newInstance();
            ActivityUtil.addFragmentToActivity(
                    getSupportFragmentManager(), projectsFragment, R.id.fl_content);
        }
        new ProjectsPresenter(mUserId, projectsFragment, new UserModel(this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_projects;
    }

}
