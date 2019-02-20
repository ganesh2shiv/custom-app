package com.core.app.media;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.caverock.androidsvg.SVG;
import com.core.app.R;

import java.io.InputStream;

import androidx.annotation.NonNull;

@GlideModule
public class ImageModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setLogLevel(Log.DEBUG)
                .setDefaultRequestOptions(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_no_image));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
                                   @NonNull Registry registry) {
        registry.register(SVG.class, PictureDrawable.class, new SvgTranscoder())
                .append(InputStream.class, SVG.class, new SvgDecoder());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}