package com.core.app.ui.epoxy;

import android.view.View;

import com.airbnb.epoxy.EpoxyHolder;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import butterknife.ButterKnife;

public abstract class BaseHolder extends EpoxyHolder {

    private View itemView;

    @CallSuper
    @Override
    protected void bindView(@NonNull View itemView) {
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public View getItemView() {
        return itemView;
    }
}