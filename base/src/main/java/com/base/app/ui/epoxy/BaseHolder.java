package com.base.app.ui.epoxy;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;

public abstract class BaseHolder extends EpoxyHolder {

    private View itemView;

    @CallSuper
    @Override
    protected void bindView(@NonNull View itemView) {
        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }
}