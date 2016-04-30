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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public ShotDetailFragment() {
    }

    public static ShotDetailFragment newInstance(String param1, String param2) {
        ShotDetailFragment fragment = new ShotDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
    protected void setData() {

    }

}
