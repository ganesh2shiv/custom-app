package com.core.app.ui.custom;

import android.os.SystemClock;
import android.view.View;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class DelayedClickListener implements View.OnClickListener {

    private static final long DEFAULT_INTERVAL = 500L;

    private final long minimumInterval;
    private Map<View, Long> lastClickMap = new WeakHashMap<>();

    protected DelayedClickListener() {
        this.minimumInterval = DEFAULT_INTERVAL;
    }

    protected DelayedClickListener(long minimumIntervalInMillis) {
        this.minimumInterval = minimumIntervalInMillis;
    }

    @Override
    public void onClick(View view) {
        Long previousClickTimestamp = lastClickMap.get(view);
        long currentTimestamp = SystemClock.uptimeMillis();

        lastClickMap.put(view, currentTimestamp);
        if (previousClickTimestamp == null || (currentTimestamp - previousClickTimestamp > minimumInterval)) {
            onClicked(view);
        }
    }

    public abstract void onClicked(View view);

}