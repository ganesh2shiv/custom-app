package com.base.app.ui.epoxy;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.ModelView;
import com.airbnb.epoxy.ModelView.Size;

@ModelView(autoLayout = Size.MATCH_WIDTH_WRAP_HEIGHT)
public class LinearCarousel extends Carousel {

    public LinearCarousel(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}