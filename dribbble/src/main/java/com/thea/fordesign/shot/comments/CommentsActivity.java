package com.thea.fordesign.shot.comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.thea.fordesign.R;
import com.thea.fordesign.UserModel;
import com.thea.fordesign.base.BaseDataBindingActivity;
import com.thea.fordesign.databinding.CommentsActBinding;
import com.thea.fordesign.util.ActivityUtil;

public class CommentsActivity extends BaseDataBindingActivity<CommentsActBinding> {
    public static final String EXTRA_SHOT_ID = "shot_id";
    public static final String EXTRA_COMMENT_URL = "comment_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setupToolbar(this, R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        CommentsFragment fragment = (CommentsFragment) getSupportFragmentManager()
                .findFragmentByTag(CommentsFragment.TAG);
        if (fragment == null) {
            fragment = CommentsFragment.newInstance();
        }
        new CommentPresenter(fragment, intent.getIntExtra(EXTRA_SHOT_ID, -1), intent.getStringExtra
                (EXTRA_COMMENT_URL), new UserModel(this));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, fragment, CommentsFragment.TAG);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comments;
    }
}
