package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.Tag;
import com.android.projectorlauncher.databinding.FragmentUnityMovieBinding;
import com.android.projectorlauncher.presenter.MoviePresenter;
import com.android.projectorlauncher.ui.view.MovieView;
import com.android.projectorlauncher.utils.ImageUtils;
import com.android.projectorlauncher.utils.NetworkUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment implements View.OnClickListener, MovieView {
    private FragmentUnityMovieBinding movieBinding;
    private MoviePresenter presenter;
    private final List<ShapeableImageView> views = new ArrayList<>();
    private final RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(false);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new MoviePresenter(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        movieBinding = FragmentUnityMovieBinding.inflate(inflater, container, false);
        return movieBinding.getRoot();
    }

    private void init() {
        views.add(movieBinding.recommend1);
        views.add(movieBinding.recommend2);
        views.add(movieBinding.recommend3);
        views.add(movieBinding.recommend4);
        views.add(movieBinding.recommend5);
        views.add(movieBinding.recommend6);
        views.add(movieBinding.recommend7);
        views.add(movieBinding.recommend8);
        views.add(movieBinding.recommend9);
        views.add(movieBinding.recommend10);
        views.add(movieBinding.recommend11);
        views.add(movieBinding.recommend12);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView(this);
        init();
        setClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init();
    }

    @Override
    public void onStop() {
        super.onStop();
        views.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void setClick() {
        for (ShapeableImageView view : views) {
            view.setOnClickListener(this);
        }
        movieBinding.search.setOnClickListener(this);
        movieBinding.more.setOnClickListener(this);
    }

    // 设置点击后的事件
    @Override
    public void onClick(View v) {
        for (int i = 0; i < views.size(); i++) {
            if (views.get(i) == v) {
                presenter.turnVideoDetailPage(i);
                break;
            }
        }
        if (v == movieBinding.recentWatch) {
            presenter.turnToRecentWatch();
        } else if (v == movieBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == movieBinding.more) {
            presenter.turnToMovieCategoryPage();
        }
    }


    @Override
    public void updateAll() {
        for (int i = 0; i < views.size(); i++) {
            updateIndex(i);
        }
    }

    @Override
    public void updateIndex(int index) {
        if (index == -1 || views.size() == 0) return;
        Glide.with(views.get(index))
                .load(NetworkUtils.isOnline(requireContext())? presenter.getImage(index) : ImageUtils.getCacheImage(requireContext(), Tag.MOVIE_IMAGE, index))
                .error(R.drawable.error_cover_can_t_found)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(options)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        views.get(index).setImageDrawable(resource);
                        ImageUtils.saveImage(requireContext(), Tag.MOVIE_IMAGE, index, resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }


}
