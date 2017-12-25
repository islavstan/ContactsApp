package com.isla.contactsapp.utils;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.isla.contactsapp.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class GlideUtils {
    private GlideUtils() {
    }

    public static void loadCircularImage(@NonNull ImageView imageView, @NonNull String imageUri) {
        Glide.with(imageView.getContext())
                .load(imageUri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_user_placeholder)
                .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                .error(R.drawable.ic_user_placeholder)
                .crossFade()
                .into(imageView);
    }

}
