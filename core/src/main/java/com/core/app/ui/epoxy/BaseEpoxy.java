package com.core.app.ui.epoxy;

import com.airbnb.epoxy.EpoxyController;
import com.core.app.BuildConfig;

import androidx.annotation.NonNull;
import timber.log.Timber;

public abstract class BaseEpoxy extends EpoxyController {

    protected BaseEpoxy() {
        setFilterDuplicates(true);
        setDebugLoggingEnabled(BuildConfig.DEBUG);
    }

    @Override
    protected void onExceptionSwallowed(@NonNull RuntimeException e) {
        Timber.e(e);
    }
}