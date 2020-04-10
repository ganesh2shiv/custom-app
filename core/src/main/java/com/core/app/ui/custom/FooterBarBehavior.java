package com.core.app.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class FooterBarBehavior extends CoordinatorLayout.Behavior<FrameLayout> {

    public FooterBarBehavior() {
    }

    public FooterBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull FrameLayout child,
                                   @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, FrameLayout child, View dependency) {
        int offset = -dependency.getTop();
        child.setTranslationY(offset);
        return true;
    }
}