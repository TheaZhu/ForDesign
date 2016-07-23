package com.thea.fordesign.user.detail;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.bean.DribbbleUser;
import com.thea.fordesign.databinding.UserDetailFragBinding;
import com.thea.fordesign.util.Preconditions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends BaseDataBindingFragment<UserDetailFragBinding> implements
        UserDetailContract.SubView {
    public static final String TAG = UserDetailFragment.class.getSimpleName();
    private static final String ARG_USER = "dribbble_user";

    private UserDetailContract.SubPresenter mPresenter;
    private DribbbleUser mUser;

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
            mUser = getArguments().getParcelable(ARG_USER);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mViewDataBinding.setActionHandler(mPresenter);
        mViewDataBinding.setUser(mUser);
    }

    @Override
    public void showBucketsUi(@NonNull String bucketsUrl) {

    }

    @Override
    public void showFollowersUi(@NonNull String followersUrl) {

    }

    @Override
    public void showTeamsUi(@NonNull String teamsUrl) {

    }

    @Override
    public void showLikesUi(@NonNull String likesUrl) {

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
    public void setPresenter(@NonNull UserDetailContract.SubPresenter presenter) {
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
