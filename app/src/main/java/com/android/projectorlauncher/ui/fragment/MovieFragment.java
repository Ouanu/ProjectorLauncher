package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentMovieBinding;
import com.android.projectorlauncher.presenter.MoviePresenter;
import com.android.projectorlauncher.ui.view.MovieView;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

public class MovieFragment extends Fragment implements View.OnClickListener, MovieView {
    private int currentIndex = 0;
    private int lastIndex = 0;
    private FragmentMovieBinding movieBinding;
    private MoviePresenter presenter;
    private View selectView;
    private final ViewTreeObserver.OnGlobalFocusChangeListener focusChangeListener = new ViewTreeObserver.OnGlobalFocusChangeListener() {
        @Override
        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
            if (oldFocus instanceof TabLayout.TabView && !(newFocus instanceof TabLayout.TabView)) {
                if (selectView != null)
                    selectView.requestFocus();
                else
                    movieBinding.recommend1.requestFocus();
            }

        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new MoviePresenter(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        movieBinding = FragmentMovieBinding.inflate(inflater, container, false);
        setImageResources();
        setFocusChange();
        setClick();
        return movieBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        movieBinding.recommend1.requestFocus();
        movieBinding.getRoot().getViewTreeObserver().addOnGlobalFocusChangeListener(focusChangeListener);
        if (presenter.sizeOfCards() == 0) {
            presenter.init();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        movieBinding.getRoot().getViewTreeObserver().removeOnGlobalFocusChangeListener(focusChangeListener);
    }

    //设置图片
    private void setImageResources() {
        Glide.with(movieBinding.image)
                .load(presenter.getImage(currentIndex))
                .error(R.color.white)
                .centerCrop()
                .into(movieBinding.image);
        movieBinding.recommend1.setImageResource(presenter.getImage(0));
        movieBinding.recommend2.setImageResource(presenter.getImage(1));
        movieBinding.recommend3.setImageResource(presenter.getImage(2));
        movieBinding.recommend4.setImageResource(presenter.getImage(3));
    }

    //设置了获取焦点的效果
    private void setFocusChange() {
        movieBinding.recommend1.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.recommend2.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.recommend3.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.recommend4.setOnFocusChangeListener(new PosterAnimationOnFocusChange());
        movieBinding.search.setOnFocusChangeListener(new MovieAnimationOnFocusChange());
        movieBinding.category.setOnFocusChangeListener(new MovieAnimationOnFocusChange());

    }

    private void setClick() {
        movieBinding.recommend1.setOnClickListener(this);
        movieBinding.recommend2.setOnClickListener(this);
        movieBinding.recommend3.setOnClickListener(this);
        movieBinding.recommend4.setOnClickListener(this);
        movieBinding.search.setOnClickListener(this);
        movieBinding.category.setOnClickListener(this);
    }

    // 设置点击后的事件
    @Override
    public void onClick(View v) {
        if (v == movieBinding.recommend1) {
            presenter.turnVideoDetailPage(0);
        } else if (v == movieBinding.recommend2) {
            presenter.turnVideoDetailPage(1);
        } else if (v == movieBinding.recommend3) {
            presenter.turnVideoDetailPage(2);
        } else if (v == movieBinding.recommend4) {
            presenter.turnVideoDetailPage(3);
        } else if (v == movieBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == movieBinding.category) {
            presenter.turnToMovieCategoryPage();
        }
    }

    @Override
    public void update() {
        setImageResources();
    }

    /**
     * 获得焦点前后的显示效果
     * PosterAnimationOnFocusChange、MovieAnimationOnFocusChange
     */
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
                        .load(presenter.getImage(currentIndex))
                        .error(R.color.white)
                        .centerCrop()
                        .into(movieBinding.image);
                lastIndex = currentIndex;
                selectView = v;
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
    class MovieAnimationOnFocusChange implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ViewCompat.animate(v)
                        .scaleX(1.05f)
                        .scaleY(1.05f)
                        .setDuration(300)
                        .translationZ(1.2f)
                        .start();
                ((CardView)v).setCardBackgroundColor(v.getContext().getColor(R.color.self_5));
                selectView = v;
            } else {
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .translationZ(1f)
                        .start();
                ((CardView)v).setCardBackgroundColor(v.getContext().getColor(R.color.self_5_un_focus));
            }
        }
    }


}
