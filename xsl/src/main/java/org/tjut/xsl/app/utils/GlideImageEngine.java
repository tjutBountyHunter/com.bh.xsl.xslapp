package org.tjut.xsl.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.engine.ImageEngine;

public class GlideImageEngine implements ImageEngine {

    private GlideImageEngine() {
    }

    private static final class Hodel {
        private static final GlideImageEngine INSTANCE = new GlideImageEngine();
    }

    public static GlideImageEngine getInstance() {
        return Hodel.INSTANCE;
    }

    @Override
    public void loadPhoto(Context context, String photoPath, ImageView imageView) {
        Glide.with(context).load(photoPath).into(imageView);
    }

    @Override
    public void loadGifAsBitmap(Context context, String gifPath, ImageView imageView) {

    }

    @Override
    public void loadGif(Context context, String gifPath, ImageView imageView) {

    }

    @Override
    public Bitmap getCacheBitmap(Context context, String path, int width, int height) throws Exception {
        return null;
    }
}
