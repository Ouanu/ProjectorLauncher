package com.android.projectorlauncher.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.databinding.FragmentUnityMovieBinding;
import com.android.projectorlauncher.presenter.MoviePresenter;
import com.android.projectorlauncher.ui.view.MovieView;
import com.bumptech.glide.Glide;

public class MovieFragment extends Fragment implements View.OnClickListener, MovieView {
    private FragmentUnityMovieBinding movieBinding;
    private MoviePresenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new MoviePresenter(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        movieBinding = FragmentUnityMovieBinding.inflate(inflater, container, false);
        setImageResources();
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
        if (presenter.sizeOfCards() == 0) {
            presenter.init();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //设置图片
    private void setImageResources() {
        Glide.with(movieBinding.recommend1)
                .load(presenter.getImage(0))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend1);
        Glide.with(movieBinding.recommend2)
                .load(presenter.getImage(1))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend2);
        Glide.with(movieBinding.recommend3)
                .load(presenter.getImage(2))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend3);
        Glide.with(movieBinding.recommend4)
                .load(presenter.getImage(3))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend4);
        Glide.with(movieBinding.recommend5)
                .load(presenter.getImage(4))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend5);
        Glide.with(movieBinding.recommend6)
                .load(presenter.getImage(5))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend6);
        Glide.with(movieBinding.recommend7)
                .load(presenter.getImage(6))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend7);
        Glide.with(movieBinding.recommend8)
                .load(presenter.getImage(7))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend8);
        Glide.with(movieBinding.recommend9)
                .load(presenter.getImage(8))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend9);
        Glide.with(movieBinding.recommend10)
                .load(presenter.getImage(9))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend10);
        Glide.with(movieBinding.recommend11)
                .load(presenter.getImage(10))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend11);
        Glide.with(movieBinding.recommend12)
                .load(presenter.getImage(11))
                .error(R.color.white)
                .fitCenter()
                .into(movieBinding.recommend12);
    }



    private void setClick() {
        movieBinding.recommend1.setOnClickListener(this);
        movieBinding.recommend2.setOnClickListener(this);
        movieBinding.recommend3.setOnClickListener(this);
        movieBinding.recommend4.setOnClickListener(this);
        movieBinding.search.setOnClickListener(this);
        movieBinding.more.setOnClickListener(this);
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
        } else if (v == movieBinding.recommend5) {
            presenter.turnVideoDetailPage(4);
        } else if (v == movieBinding.recommend6) {
            presenter.turnVideoDetailPage(5);
        } else if (v == movieBinding.recommend7) {
            presenter.turnVideoDetailPage(6);
        } else if (v == movieBinding.recommend8) {
            presenter.turnVideoDetailPage(7);
        } else if (v == movieBinding.recommend9) {
            presenter.turnVideoDetailPage(8);
        } else if (v == movieBinding.recommend10) {
            presenter.turnVideoDetailPage(9);
        } else if (v == movieBinding.recommend11) {
            presenter.turnVideoDetailPage(10);
        } else if (v == movieBinding.recommend12) {
            presenter.turnVideoDetailPage(11);
        } else if (v == movieBinding.recentWatch) {
            presenter.turnToRecentWatch();
        } else if (v == movieBinding.search) {
            presenter.turnToSearchPage();
        } else if (v == movieBinding.more) {
            presenter.turnToMovieCategoryPage();
        }
    }

    @Override
    public void update() {
        setImageResources();
    }


}
