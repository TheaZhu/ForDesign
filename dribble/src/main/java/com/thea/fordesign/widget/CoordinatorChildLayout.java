package com.thea.fordesign.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
@CoordinatorLayout.DefaultBehavior(AppBarLayout.Behavior.class)
public class CoordinatorChildLayout extends LinearLayout {

    public CoordinatorChildLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*public static class Behavior extends CoordinatorLayout.Behavior<CoordinatorChildLayout> {
        private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

        private int sinceDirectionChange;

        @Override
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, CoordinatorChildLayout child, View directTargetChild, View target, int nestedScrollAxes) {
            return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, CoordinatorChildLayout
                child, View target, int dx, int dy, int[] consumed) {
            if (dy > 0 && sinceDirectionChange < 0 || dy < 0 && sinceDirectionChange > 0) {
                child.animate().cancel();
                sinceDirectionChange = 0;
            }
            sinceDirectionChange += dy;
            if (sinceDirectionChange > child.getHeight() && child.getVisibility() == View.VISIBLE) {
                hide(child);
            } else if (sinceDirectionChange < 0 && child.getVisibility() == View.GONE) {
                show(child);
            }
        }
    }

    public static class ScrollingViewBehavior extends CoordinatorLayout.Behavior<View> {

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            // We depend on any CoordinatorChildLayout
            return dependency instanceof CoordinatorChildLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, View child,
                                              View dependency) {
            offsetChildAsNeeded(parent, child, dependency);
            return false;
        }

        private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
            final CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
            if (behavior instanceof AppBarLayout.Behavior) {
                // Offset the child, pinning it to the bottom the header-dependency, maintaining
                // any vertical gap, and overlap
                final AppBarLayout.Behavior ablBehavior = (AppBarLayout.Behavior) behavior;
                final int offset = ablBehavior.getTopBottomOffsetForScrollingSibling();
                ViewCompat.offsetTopAndBottom(child, (dependency.getBottom() - child.getTop())
                        + ablBehavior.mOffsetDelta
                        + getVerticalLayoutGap()
                        - getOverlapPixelsForOffset(dependency));
            }
        }
    }*/
}
