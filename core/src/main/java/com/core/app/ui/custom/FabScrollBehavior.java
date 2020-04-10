package com.core.app.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FabScrollBehavior extends FloatingActionButton.Behavior {

    public FabScrollBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                       @NonNull final FloatingActionButton child,
                                       @NonNull final View directTargetChild,
                                       @NonNull final View target, final int nestedScrollAxes, int type) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes, type);
    }

    @Override
    public void onNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                               @NonNull final FloatingActionButton child,
                               @NonNull final View target,
                               final int dxConsumed, final int dyConsumed, final int dxUnconsumed,
                               final int dyUnconsumed, int type, @NonNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, type, consumed);

        if (dyConsumed > 0) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            int fab_bottomMargin = layoutParams.bottomMargin;
            child.animate().translationY(child.getHeight() + fab_bottomMargin).setInterpolator(new LinearInterpolator()).start();
        } else if (dyConsumed < 0) {
            child.animate().translationY(0).setInterpolator(new LinearInterpolator()).start();
        }
    }
}