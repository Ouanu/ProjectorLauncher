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
import com.android.projectorlauncher.databinding.FragmentUnityChildrenBinding;
import com.android.projectorlauncher.presenter.ChildrenPresenter;
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

public class ChildrenFragment extends Fragment implements View.OnClickListener, MovieView {
    private FragmentUnityChildrenBinding childrenBinding;
    private ChildrenPresenter presenter;
    private final List<ShapeableImageView> views = new ArrayList<>();
    private final RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(false);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new ChildrenPresenter(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        childrenBinding = FragmentUnityChildrenBinding.inflate(inflater, container, false);
        return childrenBinding.getRoot();
    }

    private void init() {
        views.add(childrenBinding.recommend1);
        views.add(childrenBinding.recommend2);
        views.add(childrenBinding.recommend3);
        views.add(childrenBinding.recommend4);
        views.add(childrenBinding.recommend5);
        views.add(childrenBinding.recommend6);
        views.add(childrenBinding.recommend7);
        views.add(childrenBinding.recommend8);
        views.add(childrenBinding.recommend9);
        views.add(childrenBinding.recommend10);
        views.add(childrenBinding.recommend11);
        views.add(childrenBinding.recommend12);
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
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        views.clear();
    }

    private void setClick() {
        for (ShapeableImageView view : views) {
            view.setOnClickListener(this);
        }
        childrenBinding.search.setOnClickListener(this);
        childrenBinding.more.setOnClickListener(this);
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
        if (v == childrenBinding.recentWatch) {
            presenter.turnToRecentWatch();
        } else if (v == childrenBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == childrenBinding.more) {
            presenter.turnToChildCategoryPage();
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
                .load(NetworkUtils.isOnline(requireContext())? presenter.getImage(index) : ImageUtils.getCacheImage(requireContext(), Tag.CHILDREN_IMAGE, index))
                .error(R.drawable.error_cover_can_t_found)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .apply(options)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        views.get(index).setImageDrawable(resource);
                        ImageUtils.saveImage(requireContext(), Tag.CHILDREN_IMAGE, index, resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }


}
