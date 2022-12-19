package com.android.projectorlauncher.ui.design;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.Tag;
import com.android.projectorlauncher.utils.ImageUtils;
import com.android.projectorlauncher.utils.NetworkUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

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

    }

    public void setImageResource(int resId) {
        Glide.with(imageView)
                .load(resId)
                .error(R.color.none)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                .into(imageView);
    }

    public void setImageDrawable(String src, String tag, int index) {
        Glide.with(imageView)
                .load(NetworkUtils.isOnline(getContext())? src : ImageUtils.getCacheImage(getContext(), tag, index))
                .error(R.drawable.error_cover_can_t_found)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                        ImageUtils.saveImage(getContext(), tag, index, resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }


    public void setImageResource(Uri res) {
        Glide.with(imageView)
                .load(res)
                .error(R.color.none)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                .into(imageView);
    }

    public void setImageResource(Resources res) {
        Glide.with(imageView)
                .load(res)
                .error(R.color.none)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                .into(imageView);
    }

    public void setImageResource(String res) {
        Glide.with(imageView)
                .load(res)
                .error(R.color.none)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                .into(imageView);
    }

    public ImageView getImageView() {
        return imageView;
    }


}
