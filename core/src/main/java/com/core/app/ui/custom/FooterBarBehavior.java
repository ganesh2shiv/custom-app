package com.core.app.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class FooterBarBehavior extends CoordinatorLayout.Behavior<FrameLayout> {

    public FooterBarBehavior() {
    }

    public FooterBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child, View dependency) {
        int offset = -dependency.getTop();
        child.setTranslationY(offset);
        return true;
    }
}