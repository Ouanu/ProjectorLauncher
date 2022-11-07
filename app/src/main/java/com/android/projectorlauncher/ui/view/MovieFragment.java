package com.android.projectorlauncher.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentMovieBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {
    private final List<Integer> resList = new ArrayList<>();
    private int currentIndex = 0;
    private int lastIndex = 0;
    FragmentMovieBinding movieBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resList.add(R.drawable.uncharted);
        resList.add(R.drawable.batman);
        resList.add(R.drawable.moon_man);
        resList.add(R.drawable.uncharted);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        movieBinding = FragmentMovieBinding.inflate(inflater, container, false);
        movieBinding.image.setImageResource(resList.get(0));

        movieBinding.recommend1.setImageResource(resList.get(0));
        movieBinding.recommend2.setImageResource(resList.get(1));
        movieBinding.recommend3.setImageResource(resList.get(2));
        movieBinding.recommend4.setImageResource(resList.get(3));

        movieBinding.recommend1.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.recommend2.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.recommend3.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.recommend4.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.search.setOnFocusChangeListener(new MovieAnimationOnFocusChange());
        movieBinding.category.setOnFocusChangeListener(new MovieAnimationOnFocusChange());
        movieBinding.getRoot().getViewTreeObserver().addOnGlobalFocusChangeListener((oldFocus, newFocus) -> {
            Log.d("MovieFragment", "onGlobalFocusChanged: " + oldFocus + "   " + newFocus);
            if (oldFocus instanceof TabLayout.TabView && !(newFocus instanceof TabLayout.TabView)) {
                movieBinding.recommend1.requestFocus();
            }
        });
        return movieBinding.getRoot();
    }

    class PosterAnimationOnFocusChange implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v == movieBinding.recommend1) {
                currentIndex = 0;
            } else if (v == movieBinding.recommend2) {
                currentIndex = 1;
            } else if (v == movieBinding.recommend3) {
                currentIndex = 2;
            } else if (v == movieBinding.recommend4) {
                currentIndex = 3;
            }
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1.2f)
                        .start();
                if (currentIndex == lastIndex) {
                    return;
                }
                Glide.with(movieBinding.image)
                        .load(resList.get(currentIndex))
                        .error(R.color.white)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓冲disk硬盘中
                        .into(movieBinding.image);
                lastIndex = currentIndex;
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
            }
        }
    }
    static class MovieAnimationOnFocusChange implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1.2f)
                        .start();
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
            }
        }
    }


}
