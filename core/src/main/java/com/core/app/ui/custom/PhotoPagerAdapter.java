package com.core.app.ui.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.core.app.media.GlideApp;
import com.core.app.media.GlideRequests;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

public class PhotoPagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> urls;
    private boolean zoomable;
    private GlideRequests glide;

    public PhotoPagerAdapter(Context context, List<String> urls, boolean zoomable) {
        this.context = context;
        this.urls = urls;
        this.zoomable = zoomable;
        this.glide = GlideApp.with(context);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        final PhotoView photoView = new PhotoView(context);
        photoView.setZoomable(zoomable);

        if (zoomable) {
            glide.load(urls.get(position)).fitCenter().into(photoView);
        } else {
            photoView.setScaleType(ImageView.ScaleType.FIT_XY);
            glide.load(urls.get(position))
                    .override(container.getWidth(), container.getHeight())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable drawable,
                                                    @Nullable Transition<? super Drawable> transition) {
                            photoView.setImageDrawable(drawable);
                        }
                    });
        }

        container.addView(photoView);

        return photoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}