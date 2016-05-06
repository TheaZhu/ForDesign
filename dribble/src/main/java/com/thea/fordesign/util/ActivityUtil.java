package com.thea.fordesign.util;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * This provides methods to help Activities load their UI.
 *
 * @author Thea (theazhu0321@gmail.com)
 */
public class ActivityUtil {

    public static void setupToolbar(@NonNull AppCompatActivity activity, @IdRes int resId) {
        Preconditions.checkNotNull(activity, "activity cannot be null");
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) activity.findViewById(resId);
        activity.setSupportActionBar(toolbar);
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull
    Fragment fragment, @IdRes int frameId) {
        Preconditions.checkNotNull(fragmentManager, "fragmentManager cannot be null");
        Preconditions.checkNotNull(fragment, "fragment cannot be null");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
