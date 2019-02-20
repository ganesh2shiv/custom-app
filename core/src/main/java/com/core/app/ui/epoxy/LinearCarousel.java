package com.core.app.ui.epoxy;

import android.content.Context;

import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.ModelView;
import com.airbnb.epoxy.ModelView.Size;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

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