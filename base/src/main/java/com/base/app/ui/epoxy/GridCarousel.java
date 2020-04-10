package com.base.app.ui.epoxy;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.ModelView;
import com.airbnb.epoxy.ModelView.Size;

@ModelView(saveViewState = true, autoLayout = Size.MATCH_WIDTH_WRAP_HEIGHT)
public class GridCarousel extends Carousel {

    private static final int SPAN_COUNT = 2;

    public GridCarousel(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected LayoutManager createLayoutManager() {
        return new GridLayoutManager(getContext(), SPAN_COUNT, HORIZONTAL, false);
    }
}