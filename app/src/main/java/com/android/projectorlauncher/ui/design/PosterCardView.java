package com.android.projectorlauncher.ui.design;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.android.projectorlauncher.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

public class PosterCardView extends CardView {
    private ImageView imageView;
    public PosterCardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PosterCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PosterCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        imageView = new ImageView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.addView(imageView);
        this.setCardElevation(3f);
    }

    public void setImageResource(int resId) {
        Glide.with(imageView)
                .load(resId)
                .error(R.color.none)
                .transform(new CenterCrop(), new RoundedCorners(10))
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                .into(imageView);
    }


    public void setImageResource(Uri res) {
        Glide.with(imageView)
                .load(res)
                .error(R.color.none)
                .transform(new CenterCrop(), new RoundedCorners(10))
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                .into(imageView);
    }

    public void setImageResource(Resources res) {
        Glide.with(imageView)
                .load(res)
                .error(R.color.none)
                .transform(new CenterCrop(), new RoundedCorners(10))
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                .into(imageView);
    }

    public ImageView getImageView() {
        return imageView;
    }


}
