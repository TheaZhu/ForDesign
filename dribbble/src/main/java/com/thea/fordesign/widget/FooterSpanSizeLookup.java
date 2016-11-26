package com.thea.fordesign.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by Thea on 2015/8/3.
 */
public class FooterSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
    private static final String TAG = "SimpleSpanSizeLookup";
    private final GridLayoutManager mLayoutManager;

    public FooterSpanSizeLookup(@NonNull GridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    @Override
    public int getSpanSize(int position) {
        int count = mLayoutManager.getItemCount();
        if (position >= count)
            return 0;
        else if (position == count - 1 && count % 2 != 0)
            return 2;
        else
            return 1;
    }
}
