package com.thea.fordesign.team.detail;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.bucket.buckets.BucketsActivity;
import com.thea.fordesign.databinding.TeamDetailFragBinding;
import com.thea.fordesign.like.user.UserLikesActivity;
import com.thea.fordesign.project.projects.ProjectsActivity;
import com.thea.fordesign.shot.shots.ShotsActivity;
import com.thea.fordesign.user.followers.FollowersActivity;
import com.thea.fordesign.user.followers.FollowersFragment;
import com.thea.fordesign.user.users.UsersActivity;
import com.thea.fordesign.util.Preconditions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDetailFragment extends BaseDataBindingFragment<TeamDetailFragBinding> implements
        TeamDetailContract.View {
    public static final String TAG = TeamDetailFragment.class.getSimpleName();

    private TeamDetailContract.Presenter mPresenter;

    public TeamDetailFragment() {
    }

    public static TeamDetailFragment newInstance() {
        return new TeamDetailFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_team_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBinding.setActionHandler(mPresenter);
    }

    @Override
    public void showTeam(DribbbleUser team) {
        mViewDataBinding.setTeam(team);
    }

    @Override
    public void showBucketsUi(@NonNull String bucketsUrl) {
        Intent intent = new Intent(getContext(), BucketsActivity.class);
        intent.putExtra(BucketsActivity.EXTRA_TITLE, getString(R.string.title_buckets));
        intent.putExtra(BucketsActivity.EXTRA_BUCKETS_URL, bucketsUrl);
        startActivity(intent);
    }

    @Override
    public void showProjectsUi(int teamId) {
        Intent intent = new Intent(getContext(), ProjectsActivity.class);
        intent.putExtra(ProjectsActivity.EXTRA_TITLE, getString(R.string.title_projects));
        intent.putExtra(ProjectsActivity.EXTRA_USER_ID, teamId);
        startActivity(intent);
    }

    @Override
    public void showMembersUi(@NonNull String membersUrl) {
        Intent intent = new Intent(getContext(), UsersActivity.class);
        intent.putExtra(UsersActivity.EXTRA_TITLE, getString(R.string.title_members));
        intent.putExtra(UsersActivity.EXTRA_USERS_URL, membersUrl);
        startActivity(intent);
    }

    @Override
    public void showFollowersUi(@NonNull String followersUrl) {
        Intent intent = new Intent(getContext(), FollowersActivity.class);
        intent.putExtra(FollowersActivity.EXTRA_TITLE, getString(R.string.title_followers));
        intent.putExtra(FollowersActivity.EXTRA_FOLLOWER_URL, followersUrl);
        intent.putExtra(FollowersActivity.EXTRA_ITEM_TYPE, FollowersFragment.TYPE_FOLLOWER);
        startActivity(intent);
    }

    @Override
    public void showTeamShotsUi(@NonNull String teamShotsUrl) {
        Intent intent = new Intent(getContext(), ShotsActivity.class);
        intent.putExtra(ShotsActivity.EXTRA_TITLE, getString(R.string.title_my_shots));
        intent.putExtra(ShotsActivity.EXTRA_SHOTS_URL, teamShotsUrl);
        startActivity(intent);
    }

    @Override
    public void showLikesUi(@NonNull String likesUrl) {
        Intent intent = new Intent(getContext(), UserLikesActivity.class);
        intent.putExtra(UserLikesActivity.EXTRA_TITLE, getString(R.string.title_user_likes));
        intent.putExtra(UserLikesActivity.EXTRA_LIKE_URL, likesUrl);
        startActivity(intent);
    }

    @Override
    public void showWeb(@NonNull String web) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(web)));
    }

    @Override
    public void showTwitter(@NonNull String twitter) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitter)));
    }

    @Override
    public void setPresenter(@NonNull TeamDetailContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter, "presenter cannot be null");
    }

    @Override
    public void showSnack(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
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
}
