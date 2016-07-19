package com.thea.fordesign.user.detail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.UserDetailFragBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends BaseDataBindingFragment<UserDetailFragBinding> implements
        UserDetailContract.SubView {
    private static final String TAG = UserDetailFragment.class.getSimpleName();
    private static final String ARG_USER = "dribbble_user";

    private UserDetailContract.Presenter mPresenter;

    public UserDetailFragment() {
    }

    public static UserDetailFragment newInstance(DribbbleUser user) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    public void showUser(DribbbleUser user) {

    }

    @Override
    public void showShotsUi(@NonNull String shotsUrl) {

    }

    @Override
    public void showBucketsUi(@NonNull String bucketsUrl) {

    }

    @Override
    public void showProjectsUi(@NonNull String projectsUrl) {

    }

    @Override
    public void showFollowersUi(@NonNull String followersUrl) {

    }

    @Override
    public void showFollowingsUi(@NonNull String followingsUrl) {

    }

    @Override
    public void showLikesUi(@NonNull String likesUrl) {

    }

    @Override
    public void showWeb(@NonNull String web) {

    }

    @Override
    public void showTwitter(@NonNull String twitter) {

    }

    @Override
    public void setPresenter(@NonNull UserDetailContract.SubPresenter presenter) {

    }

    @Override
    public void showSnack(String msg) {

    }

    @Override
    public void showSnack(@StringRes int resId) {

    }
}
