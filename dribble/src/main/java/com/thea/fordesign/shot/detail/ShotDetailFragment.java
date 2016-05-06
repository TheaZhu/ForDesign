package com.thea.fordesign.shot.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.thea.fordesign.R;
import com.thea.fordesign.base.BaseDataBindingFragment;
import com.thea.fordesign.databinding.ShotsFragmentBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShotDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShotDetailFragment extends BaseDataBindingFragment<ShotsFragmentBinding> {
    private static final String ARG_SHOT_ID = "shot_id";

    private int mShotId = -1;

    public ShotDetailFragment() {
    }

    public static ShotDetailFragment newInstance(int shotId) {
        ShotDetailFragment fragment = new ShotDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SHOT_ID, shotId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShotId = getArguments().getInt(ARG_SHOT_ID);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shot_detail_fragment;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected void setData() {

    }

}
